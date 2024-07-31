package com.bubaum.pairing_server.payment.domain.dto

import com.bubaum.pairing_server.enums.PayType
import com.bubaum.pairing_server.payment.domain.entity.*
import java.math.BigDecimal

class PaymentResponseDto(

    var id: Long = -1,

    var paymentKey: String = "",

    var type: String = "",

    var orderId: String = "",

    var orderName: String = "",

    var mId: String = "",

    var currency: String = "",

    var method: String = "",

    var totalAmount: BigDecimal = BigDecimal.ZERO,

    var balanceAmount: BigDecimal = BigDecimal.ZERO,

    var status: String = "",

    var requestedAt: String = "",

    var approvedAt: String = "",

    var useEscrow: Boolean = false,

    var card: Card? = null,

    var virtualAccount: VirtualAccount? = null,

    var mobilePhone: MobilePhone? = null,

    var giftCertificate: GiftCertificate? = null,

    var transfer: Transfer? = null,

    var easyPay: EasyPayResponseDto? = null,
) {

    companion object{
        fun of(payment: Payment) : PaymentResponseDto {
            val paymentResponseDto = PaymentResponseDto()
            paymentResponseDto.id = payment.id!!
            paymentResponseDto.paymentKey = payment.paymentKey
            paymentResponseDto.type = payment.type
            paymentResponseDto.orderId = payment.orderId
            paymentResponseDto.orderName = payment.orderName
            paymentResponseDto.mId = payment.mId
            paymentResponseDto.currency = payment.currency
            paymentResponseDto.method = payment.method
            paymentResponseDto.totalAmount = payment.totalAmount
            paymentResponseDto.balanceAmount = payment.balanceAmount
            paymentResponseDto.status = payment.status
            paymentResponseDto.requestedAt = payment.requestedAt
            paymentResponseDto.approvedAt = payment.approvedAt
            paymentResponseDto.useEscrow = payment.useEscrow
            when (payment.method) {
                PayType.EASY_PAY.value -> paymentResponseDto.easyPay = EasyPayResponseDto.of(payment.easyPay!!)

                //TODO: 각 결제 수단에 대한 응답 DTO 추가해야됌 (직렬화 오류)
                PayType.CARD.value -> paymentResponseDto.card = payment.card
                PayType.VIRTUAL_ACCOUNT.value -> paymentResponseDto.virtualAccount = payment.virtualAccount
                PayType.MOBILE_PHONE.value -> paymentResponseDto.mobilePhone = payment.mobilePhone
                PayType.GIFT_CERTIFICATE.value -> paymentResponseDto.giftCertificate = payment.giftCertificate
                PayType.TRANSFER.value -> paymentResponseDto.transfer = payment.transfer
            }

            return paymentResponseDto
        }
    }
}