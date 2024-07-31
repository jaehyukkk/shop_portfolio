package com.bubaum.pairing_server.order.domain.dto

class OrderRequestDto(
    val orderId: String,
    val amount: Int,
    val paymentType: String,
    val paymentKey: String,
) {
}