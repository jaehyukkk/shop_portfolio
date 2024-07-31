package com.bubaum.pairing_server.point.domain.entity

import com.bubaum.pairing_server.enums.PointDealReason
import com.bubaum.pairing_server.enums.PointType
import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Point(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Comment("적립")
    val pointsEarned : Int? = 0,

    @Comment("사용")
    val pointsUsed : Int? = 0,

    @Comment("잔여")
    val pointsBalance : Int,

    @Comment("적립/사용 사유")
    val reason: PointDealReason,

    @Comment("적립/사용 타입")
    val pointType: PointType,

) {
}