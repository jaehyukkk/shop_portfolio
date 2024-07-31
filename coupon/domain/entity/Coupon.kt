package com.bubaum.pairing_server.coupon.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.enums.CouponType
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val name: String,
    val description: String,
    val isEnabled: Boolean = true,
    val couponType: CouponType,
    val discount: Int,
    val expiredAt: LocalDateTime,
): Base() {
}