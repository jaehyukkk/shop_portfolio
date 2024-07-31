package com.bubaum.pairing_server.order.service

import com.bubaum.pairing_server.cartitem.service.CartItemService
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.order.domain.dto.OrderCancelResponseDto
import com.bubaum.pairing_server.order.domain.dto.OrderRequestDto
import com.bubaum.pairing_server.order.domain.dto.OrderTempDto
import com.bubaum.pairing_server.payment.domain.dto.toss.TossPaymentsRequestDto
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.member.service.MemberService
import com.bubaum.pairing_server.order.domain.entity.Order
import com.bubaum.pairing_server.payment.domain.repository.card.CardRepository
import com.bubaum.pairing_server.order.domain.repository.OrderRepository
import com.bubaum.pairing_server.orderitem.domain.dto.*
import com.bubaum.pairing_server.orderitem.domain.entity.OrderItem
import com.bubaum.pairing_server.orderitem.domain.repository.OrderItemRepository
import com.bubaum.pairing_server.payment.domain.repository.payment.easypay.EasyPayRepository
import com.bubaum.pairing_server.payment.domain.repository.payment.payment.PaymentRepository
import com.bubaum.pairing_server.payment.domain.repository.paymentcancel.PaymentCancelRepository
import com.bubaum.pairing_server.coupon.service.CouponService
import com.bubaum.pairing_server.deliveryconfig.domain.dto.DeliveryConfigResponseDto
import com.bubaum.pairing_server.deliveryconfig.domain.entity.DeliveryConfig
import com.bubaum.pairing_server.deliveryconfig.service.DeliveryConfigService
import com.bubaum.pairing_server.deliveryinfo.service.DeliveryInfoService
import com.bubaum.pairing_server.enums.*
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.payment.domain.entity.Payment
import com.bubaum.pairing_server.global.service.RedisService
import com.bubaum.pairing_server.point.service.PointService
import com.bubaum.pairing_server.sendcost.service.SendCostService
import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.user.service.UserService
import com.bubaum.pairing_server.utils.enum.result
import com.bubaum.pairing_server.utils.global.OrderErrorObject
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.util.*


@Service
class OrderService(
    @Value("\${toss-payments.secret}") val secretKey: String,
    private val redisService: RedisService,
    private val cartItemService: CartItemService,
    private val couponService: CouponService,
    private val restTemplate: RestTemplate,
    private val paymentRepository: PaymentRepository,
    private val easyPayRepository: EasyPayRepository,
    private val cardRepository: CardRepository,
    private val orderRepository: OrderRepository,
    private val memberService: MemberService,
    private val userService: UserService,
    private val orderItemRepository: OrderItemRepository,
    private val paymentCancelRepository: PaymentCancelRepository,
    private val deliveryInfoService: DeliveryInfoService,
    private val deliveryConfigService: DeliveryConfigService,
    private val sendCostService: SendCostService,
    private val pointService: PointService
) {
    @Transactional
    fun orderApproval(orderRequestDto: OrderRequestDto, userIdx: Long) {
        val gson = Gson()
        try{
            //----------- 유효성 검증 start ----------------

            //redis 캐싱해둔 주문 정보 가져오기
            val tmp = redisService.getObjectValue(orderRequestDto.orderId, OrderTempDto::class.java)
                ?: throw BaseException(ErrorCode.NOT_FOUND_ORDER)

            if (tmp.userIdx != userIdx) {
                throw BaseException(ErrorCode.NOT_MATCH_ORDER)
            }

            var totalAmount: Int = 0
            //유저 쿠폰 리스트
            val userCouponList = couponService.getUserCoupon(userIdx)
            //주문상품
            val orderItemList: MutableList<OrderItem> = mutableListOf()
            //주문 배송지 정보
            val deliveryInfo = deliveryInfoService.get(tmp.deliveryInfoIdx!!)
            val user : User = userService.getUser(userIdx)
            var accumulatedPoints = 0
            for(orderProduct in tmp.orderProducts!!){

                //--주문할 상품 객체 미리 만들어놓음--
                val cart = cartItemService.getCart(orderProduct.cartItemIdx!!)
                val orderItem = OrderItem(
                    count = cart.count,
                    product = cart.product,
                    user = user,
                    productOptionGroup = cart.productOptionGroup,
                )

                if(cart.product?.pointStandard == PointStandard.FIXED){
                    accumulatedPoints += cart.product!!.point
                }
                if(cart.product?.pointStandard == PointStandard.SELLING){
                    accumulatedPoints += (cart.product!!.point * cart.product!!.price) / 100
                }

                //--주문할 상품 객체 미리 만들어놓음--

                //쿼리에서 상품 + 옵션 + 쿠폰할인 total 값 계산
                val totalPrice : Int = cartItemService.getCartItemTotalPrice(userIdx, orderProduct.cartItemIdx, orderProduct.couponIdx ?: 0L)
                //쿠폰을 사용한 상품의 경우
                if (orderProduct.useCoupon == true) {
                    val coupon = userCouponList.stream().filter { c-> c.idx == orderProduct.couponIdx }.findFirst()
                        .orElseThrow { BaseException(ErrorCode.ENTITY_NOT_FOUND) }
                    if (coupon.expiredAt.isBefore(LocalDateTime.now())) {
                        throw BaseException(ErrorCode.EXPIRED_COUPON)
                    }
                    //쿠폰 사용 처리
                    couponService.applyPublishCoupon(orderProduct.couponIdx!!)
                    //주문 상품객체에 쿠폰 저장
                    orderItem.coupon = couponService.getPublishCoupon(orderProduct.couponIdx)
                }
                totalAmount += totalPrice
                orderItem.resultAmount = totalPrice
                orderItemList.add(orderItem)
            }
            //--------배송비검사---------


            //기본 배송료
            val deliveryConfig : DeliveryConfigResponseDto? = deliveryConfigService.get()
            if (deliveryConfig != null) {
                if (deliveryConfig.type == DeliveryType.DIFFERENCES_AMOUNT.code) {
                    deliveryConfig.differentialPrices!!.sortedBy { it.limitPrice }
                        .find{it.limitPrice >= totalAmount}?.let{
                            totalAmount += it.price
                        }
                }
            }

            //추가 배송금 지역 검사
            val sendCostPrice = sendCostService.calculateShippingCost(deliveryInfo.postCode.toInt())
            totalAmount += sendCostPrice

            //포인트 검사
            if (tmp.point != null) {
                val userPoint = pointService.getUserPoint(user)
                if(userPoint < tmp.point) throw BaseException(ErrorCode.NOT_ENOUGH_POINT)
                totalAmount -= tmp.point
            }


            if (totalAmount != tmp.amount || tmp.amount != orderRequestDto.amount) {
                println("totalAmount : $totalAmount")
                println("tmp.amount : ${tmp.amount}")
                println("orderRequestDto.amount : ${orderRequestDto.amount}")

                throw BaseException(ErrorCode.NOT_MATCH_ORDER_PRICE)
            }
            //----------- 유효성 검증 end ----------------

            //----------- 결제 로직 start ----------------

            val encoder = Base64.getEncoder()
            val encodedBytes = encoder.encode(("$secretKey:").toByteArray(StandardCharsets.UTF_8))
            val authorizations = "Basic " + String(encodedBytes)


            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            httpHeaders.set("Authorization", authorizations)
            val request = HttpEntity<Any>(orderRequestDto, httpHeaders)
            val response : ResponseEntity<TossPaymentsRequestDto> = restTemplate.postForEntity("https://api.tosspayments.com/v1/payments/confirm", request, TossPaymentsRequestDto::class.java)
            val tossPaymentDto : TossPaymentsRequestDto? = response.body

            var payment : Payment? = null
            //간편결제일 경우
            if (tossPaymentDto?.method == PayType.EASY_PAY.value) {
                val easyPay = easyPayRepository.save(tossPaymentDto.easyPay!!.toEntity())
                payment = tossPaymentDto.toEntity()
                payment.easyPay = easyPay
            }
            if (tossPaymentDto?.method == PayType.CARD.value) {
                val card = cardRepository.save(tossPaymentDto.card!!.toEntity())
                payment = tossPaymentDto.toEntity()
                payment.card = card
            }
//            if (tossPaymentDto?.method == PayType.MOBILE_PHONE.value) {
//                payment = tossPaymentDto.toEntity()
//            }
            val savePayment : Payment =
                paymentRepository.save(payment ?: throw
                    BaseException(ErrorCode.PAYMENT_ERROR)
                )
            //TODO: 간편결제(이지페이) 외 다른 결제 수단들에 대한 처리

            //----------- 결제 로직 end ----------------

            //----------- 주문 정보 저장 start ----------------

            //주문생성
            val order: Order = orderRepository.save(
                Order(
                payment = savePayment,
                member = memberService.getMember(userIdx),
                deliveryInfo = deliveryInfo)
            )

            for (orderItem in orderItemList) {
                //위에서 미리 저장해놓은 주문 상품객체를 이용해서 save
                val createOrderItem = orderItemRepository.save(orderItem)
                //생성한 주문에 주문아이템 연관관계 매핑
                order.addOrderItem(createOrderItem)
            }
            //----------- 주문 정보 저장 end ----------------

            //----------- 포인트 적립 및 차감 start ----------------

            //포인트 사용처리
            if (tmp.point != null) {
                pointService.create(user, PointType.USED, PointDealReason.PRODUCT_BUYING, tmp.point)
            }

            //포인트 적립처리
            pointService.create(user, PointType.EARNED, PointDealReason.PRODUCT_BUYING, accumulatedPoints)
            //----------- 포인트 적립 및 차감 end ----------------

            //----------- 장바구니 삭제 start ----------------
            cartItemService.deleteCartItems(
                tmp.orderProducts.map { it.cartItemIdx!! },
                userIdx
            )

        } catch (e : HttpClientErrorException) {
            e.printStackTrace()
            val errorObj = gson.fromJson(e.responseBodyAsString, OrderErrorObject::class.java)
            throw CustomMessageRuntimeException(ErrorCode.BAD_REQUEST, errorObj.message)
        }

    }

    @Transactional
    fun orderCancel(orderCancelResponseDto: OrderCancelResponseDto, userIdx: Long) {
        //주문정보 검색
        val paymentKeyResponseDto = paymentRepository.getPaymentKey(orderCancelResponseDto.orderItemIdx, userIdx)
            ?: throw BaseException(ErrorCode.ENTITY_NOT_FOUND)
        //토스페이먼츠 환불 요청 URL
        val cancelURL = "https://api.tosspayments.com/v1/payments/${paymentKeyResponseDto.paymentKey}/cancel"

        //토스페이먼츠 REST API 요청 준비
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.set("Authorization", "Basic $secretKey")
        val requestBody = mutableMapOf<String, Any>()
        requestBody["cancelReason"] = orderCancelResponseDto.cancelReason
        requestBody["cancelAmount"] = paymentKeyResponseDto.resultAmount
        val request = HttpEntity<Any>(requestBody, httpHeaders)

        //환불요청보내기
        val response: ResponseEntity<TossPaymentsRequestDto> =
            restTemplate.postForEntity(cancelURL, request, TossPaymentsRequestDto::class.java)

        //환불요청 결과 response 를 TossPaymentsRequestDto 객체로 받음
        val tossPaymentsRequestDto : TossPaymentsRequestDto? = response.body
        val payment = paymentRepository.findByOrderId(tossPaymentsRequestDto!!.orderId) ?:
            throw BaseException(ErrorCode.ENTITY_NOT_FOUND)

        if (response.statusCode == HttpStatus.OK) {
            paymentRepository.setPaymentCancelData(
                id = payment.id!!,
                totalAmount = tossPaymentsRequestDto.totalAmount,
                balanceAmount = tossPaymentsRequestDto.balanceAmount,
                status = tossPaymentsRequestDto.status,
                suppliedAmount = tossPaymentsRequestDto.suppliedAmount ?: BigDecimal.ZERO,
                vat = tossPaymentsRequestDto.vat ?: BigDecimal.ZERO
            )
        }
        val cancelsSize = tossPaymentsRequestDto.cancels?.size
        if (cancelsSize != null && cancelsSize > 0) {
            val paymentCancel = tossPaymentsRequestDto.cancels[cancelsSize - 1]
            payment.addCancels(paymentCancelRepository.save(paymentCancel.toEntity(payment.orderId)))
        }

        orderItemRepository.modifyStatus(orderCancelResponseDto.orderItemIdx, OrderStatus.CANCEL)
    }

//    fun getOrderItemList(userIdx : Long) : List<OrderListResponseDto>{
//        return orderItemRepository.getOrderItemList(userIdx)
//    }

    fun getDashboardOrderInfos(pageDto: PageDto, dashboardOrderItemSearchDto: DashboardOrderItemSearchDto) : Page<DashboardOrderItemListResponseDto>{
        return orderItemRepository.getDashboardOrderItems(pageDto.pageable(), dashboardOrderItemSearchDto)
    }
    fun getCheckData(orderId: String): OrderTempDto {
        return redisService.getObjectValue(orderId, OrderTempDto::class.java) ?: throw BaseException(ErrorCode.NOT_FOUND_ORDER)
    }

    fun deleteCheckData() {
        redisService.delete("orderCheckDto")
    }

    fun getDashboardDeliveryManagementList(pageDto: PageDto, dashboardDeliveryManagementSearchDto: DashboardDeliveryManagementSearchDto) : Page<DashboardDeliveryManagementListDto> {
        return orderItemRepository.getDashboardDeliveryManagementList(pageDto.pageable(), dashboardDeliveryManagementSearchDto)
    }

    fun delivery(dispatchRequestDto: DispatchRequestDto) {
        for(dispatchRequestItem in dispatchRequestDto.dispatchItems){
            orderItemRepository.modifyDispatchInfo(
                idx = dispatchRequestItem.idx
                , deliveryCompany = DeliveryCompany.getByCode(dispatchRequestItem.deliveryCompanyCode) ?: throw BaseException(ErrorCode.NOT_FOUND_ORDER)
                , invoiceNumber = dispatchRequestItem.invoiceNumber
                , status = OrderStatus.DELIVERY)
        }
    }

    fun getUserOrderItems(pageDto: PageDto, userIdx: Long): Page<UserOrderItemListResponseDto> {
        return orderItemRepository.getUserOrderItems(pageDto.pageable(), userIdx)
    }

    fun getReviewOrderItem(idx: Long, userIdx: Long) : OrderItem {
        return orderItemRepository.findByIdxAndUserIdxAndStatus(idx, userIdx, OrderStatus.COMPLETE) ?: throw BaseException(ErrorCode.NOT_FOUND_ORDER)
    }
}
