package com.bubaum.pairing_server.coupon.domain.dto

import com.bubaum.pairing_server.enums.CouponType
import com.querydsl.core.annotations.QueryProjection

data class CouponResponseDto @QueryProjection constructor(
    val idx: Long,
    val name: String,
    val description: String,
    val isEnabled: Boolean,
    val couponType: CouponType,
    val discount: Int,
    val publishCount: Long
) {
}