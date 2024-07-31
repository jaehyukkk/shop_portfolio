package com.bubaum.pairing_server.point.domain.repository

import com.bubaum.pairing_server.point.domain.entity.Point
import com.bubaum.pairing_server.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PointRepository : JpaRepository<Point, Long> {

    fun findFirstByUserOrderByIdxDesc(user: User): Point?
}