package com.bubaum.pairing_server.point.service

import com.bubaum.pairing_server.enums.PointDealReason
import com.bubaum.pairing_server.enums.PointType
import com.bubaum.pairing_server.point.domain.dto.PointRequestDto
import com.bubaum.pairing_server.point.domain.entity.Point
import com.bubaum.pairing_server.point.domain.repository.PointRepository
import com.bubaum.pairing_server.user.domain.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val pointRepository: PointRepository
) {

    //point 적립 or 차감
    @Transactional
    fun create(user: User, pointType: PointType, pointDealReason: PointDealReason, point: Int): Point {
//        pointRequestDto.pointsBalance = (pointRequestDto.pointsEarned ?: 0)  + pointRequestDto.pointsBalance - (pointRequestDto.pointsUsed ?: 0)
        return pointRepository.save(Point(
            user = user,
            pointsBalance = point,
            pointType = pointType,
            reason = pointDealReason
        ))
    }

    fun getUserPoint(user: User): Int {
        return pointRepository.findFirstByUserOrderByIdxDesc(user)?.pointsBalance ?: 0
    }
}