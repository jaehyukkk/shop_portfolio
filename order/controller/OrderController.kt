package com.bubaum.pairing_server.order.controller

import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.order.domain.dto.OrderCancelResponseDto
import com.bubaum.pairing_server.order.domain.dto.OrderRequestDto
import com.bubaum.pairing_server.order.domain.dto.OrderTempDto
import com.bubaum.pairing_server.deliveryinfo.service.DeliveryInfoService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.order.service.OrderService
import com.bubaum.pairing_server.global.service.RedisService
import com.bubaum.pairing_server.orderitem.domain.dto.*
import com.bubaum.pairing_server.utils.enum.result
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/order")
class OrderController(
    private val redisService: RedisService,
    private val orderService: OrderService,
    private val deliveryInfoService: DeliveryInfoService
) {


    @Operation(summary = "주문 임시 저장 API")
    @PostMapping("/temp")
    fun checkDataSave(@RequestBody orderTempDto: OrderTempDto, principal: Principal): ResponseEntity<Void> {
        orderTempDto.userIdx = principal.name.toLong()
        redisService.setObjectValues(orderTempDto.orderId.toString(), orderTempDto)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "주문 승인 API")
    @PostMapping()
    fun orderApproval(@RequestBody orderRequestDto: OrderRequestDto, principal: Principal): ResponseEntity<Void> {
        orderService.orderApproval(orderRequestDto, principal.name.toLong())
        return ResponseEntity.ok().build()
    }
    @Operation(summary = "주문 취소 요청 API")
    @PostMapping("/cancel")
    fun orderCancel(@RequestBody orderCancelResponseDto: OrderCancelResponseDto, principal: Principal): ResponseEntity<Void> {
        orderService.orderCancel(orderCancelResponseDto, principal.name.toLong())
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "관리자페이지 주문내역 리스트 API")
    @GetMapping("/info/dashboard")
    fun getDashboardOrderInfos(
        pageDto: PageDto,
        dashboardOrderItemSearchDto: DashboardOrderItemSearchDto
    ): ResponseEntity<Page<DashboardOrderItemListResponseDto>> {
        return ResponseEntity.ok(orderService.getDashboardOrderInfos(pageDto, dashboardOrderItemSearchDto))
    }

    @Operation(summary = "발주/발송관리 리스트 API")
    @GetMapping("/delivery-management")
    fun getDeliveryManagementOrderInfos(
        pageDto: PageDto,
        searchDto: DashboardDeliveryManagementSearchDto
    ): ResponseEntity<Page<DashboardDeliveryManagementListDto>> {
        return ResponseEntity.ok(orderService.getDashboardDeliveryManagementList(pageDto, searchDto))
    }

    @Operation(summary = "발송처리 API")
    @PutMapping("/dispatch")
    fun delivery(
        @RequestBody dispatchRequestDto: DispatchRequestDto
    ): ResponseEntity<Void> {
        orderService.delivery(dispatchRequestDto)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "회원 주문내역 리스트 API")
    @GetMapping()
    fun getUserOrderItems(
        pageDto: PageDto,
        principal: Principal
    ): ResponseEntity<Page<UserOrderItemListResponseDto>> {
        return ResponseEntity.ok(orderService.getUserOrderItems(pageDto, principal.name.toLong()))
    }

//    @Operation(summary = "주문 리스트 조회 API")
//    @GetMapping()
//    fun getOrderList(principal: Principal): ResponseEntity<List<OrderListResponseDto>> {
//        return ResponseEntity.ok(orderService.getOrderItemList(principal.name.toLong()))
//    }

    @GetMapping("/check-test/{orderId}")
    fun tess(@PathVariable orderId: String): OrderTempDto {
        return orderService.getCheckData(orderId)
    }

    @GetMapping("/check-test-delete")
    fun tess1(): String {
        orderService.deleteCheckData()
        return "삭제 완료"
    }
}
