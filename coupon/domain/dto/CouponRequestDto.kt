package com.bubaum.pairing_server.coupon.domain.dto

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.enums.CouponType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class CouponRequestDto(
    @Schema(description = "쿠폰명", defaultValue = "할인 쿠폰")
    val name: String,
    @Schema(description = "쿠폰 설명", defaultValue = "할인 쿠폰 설명")
    val description: String,
    @Schema(description = "쿠폰 타입 (0: 퍼센트 기준, 1: 금액 기준)", defaultValue = "1")
    val couponType: CouponType,
    @Schema(description = "할인 금액", defaultValue = "1000")
    val discount: Int,
    @Schema(description = "쿠폰 만료일", defaultValue = "2023-12-22T00:00:00")
    val expiredAt: LocalDateTime,
) {

    fun toEntity() : Coupon {
        return Coupon(
            name = name,
            description = description,
            couponType = couponType,
            discount = discount,
            expiredAt = expiredAt,
        )
    }
}