package com.bubaum.pairing_server.coupon.domain.repository

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface CouponRepository : JpaRepository<Coupon, Long>, CouponRepositoryCustom {
}