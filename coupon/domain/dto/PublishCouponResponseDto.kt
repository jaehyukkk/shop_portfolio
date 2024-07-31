package com.bubaum.pairing_server.coupon.domain.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class PublishCouponResponseDto @QueryProjection constructor(
    val idx: Long,
    val couponIdx: Long,
    val couponName: String,
    val couponIsEnabled: Boolean,
    val couponDescription: String,
    val expiredAt: LocalDateTime,
    val isUsed: Boolean? = false,
    val usedAt: LocalDateTime? = null,
    val userIdx: Long? = null,
    val userId: String? = null,
) {
}