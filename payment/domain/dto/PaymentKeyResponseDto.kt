package com.bubaum.pairing_server.payment.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class PaymentKeyResponseDto @QueryProjection constructor(
    val paymentKey: String,
    val resultAmount: Int
) {
}