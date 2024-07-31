package com.bubaum.pairing_server.member.domain.entity

import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade
import com.bubaum.pairing_server.publishcoupon.domain.entity.PublishCoupon
import com.bubaum.pairing_server.user.domain.entity.User
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Member(
    @Id
    @Comment("유저 pk")
    val idx: Long? = null,
    @OneToOne(fetch = FetchType.LAZY)
    val user : User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_grade_idx")
    val memberGrade: MemberGrade? = null,

    val socialType: SocialType? = null
): Base() {

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "member")
    var publishCoupons: MutableSet<PublishCoupon> = LinkedHashSet()
    fun addPublishCoupon(publishCoupon: PublishCoupon) {
        publishCoupons.add(publishCoupon)
        publishCoupon.member = this
    }

}
