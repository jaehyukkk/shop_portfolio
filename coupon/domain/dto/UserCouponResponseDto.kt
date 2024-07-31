package com.bubaum.pairing_server.coupon.domain.dto

import com.bubaum.pairing_server.enums.CouponType
import java.time.LocalDateTime

class UserCouponResponseDto(
    val idx: Long,
    val name: String,
    val expiredAt: LocalDateTime,
    val couponType: CouponType,
    val discount: Int,
) {
}