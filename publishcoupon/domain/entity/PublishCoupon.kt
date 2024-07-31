package com.bubaum.pairing_server.publishcoupon.domain.entity

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.member.domain.entity.Member
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class PublishCoupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val isUsed: Boolean = false,
    val usedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_idx")
    val coupon: Coupon,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    var member: Member? = null,
): Base() {
}