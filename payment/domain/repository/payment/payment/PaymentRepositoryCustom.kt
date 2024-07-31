package com.bubaum.pairing_server.payment.domain.repository.payment.payment

import com.bubaum.pairing_server.payment.domain.dto.PaymentKeyResponseDto

interface PaymentRepositoryCustom {
    fun getPaymentKey(cartIdx: Long, userIdx: Long) : PaymentKeyResponseDto?

    //orderid로 payment정보 가져오기
}