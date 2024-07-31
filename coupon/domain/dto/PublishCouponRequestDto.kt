package com.bubaum.pairing_server.coupon.domain.dto

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.publishcoupon.domain.entity.PublishCoupon
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema

class PublishCouponRequestDto(
    @Schema(description = "쿠폰 idx", defaultValue = "1")
    val couponIdx: Long,
    @Schema(description = "쿠폰 발급 수량", defaultValue = "5")
    val count: Int,
) {
    @JsonIgnore
    var coupon: Coupon? = null

    fun toEntity(coupon: Coupon) : PublishCoupon {
        return PublishCoupon(
            coupon = coupon,
        )
    }
}