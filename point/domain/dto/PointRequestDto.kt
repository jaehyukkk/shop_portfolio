package com.bubaum.pairing_server.point.domain.dto

import com.bubaum.pairing_server.enums.PointDealReason
import com.bubaum.pairing_server.enums.PointType
import com.bubaum.pairing_server.point.domain.entity.Point
import com.bubaum.pairing_server.user.domain.entity.User

class PointRequestDto(
    val user: User,

    // 적립
    val pointsEarned : Int? = 0,

    // 사용
    val pointsUsed : Int? = 0,

    // 잔여
    var pointsBalance : Int,

    // 적립/사용 사유
    val reason: PointDealReason,

    // 적립/사용 타입
    val pointType: PointType,
) {

    fun toEntity() : Point {
        return Point(
            user = user,
            pointsEarned = pointsEarned,
            pointsUsed = pointsUsed,
            pointsBalance = pointsBalance,
            reason = reason,
            pointType=pointType
        )
    }
}