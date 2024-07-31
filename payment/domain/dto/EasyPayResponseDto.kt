package com.bubaum.pairing_server.payment.domain.dto

import com.bubaum.pairing_server.payment.domain.entity.EasyPay
import java.math.BigDecimal

class EasyPayResponseDto(
    val id: Long,

    val provider: String,

    val amount: BigDecimal,

    val discountAmount : BigDecimal? = null,
) {

    companion object{
        fun of(easyPay: EasyPay): EasyPayResponseDto {
            return EasyPayResponseDto(
                id = easyPay.id!!,
                provider = easyPay.provider,
                amount = easyPay.amount,
                discountAmount = easyPay.discountAmount
            )
        }
    }
}