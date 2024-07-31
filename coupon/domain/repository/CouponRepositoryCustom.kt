package com.bubaum.pairing_server.coupon.domain.repository

import com.bubaum.pairing_server.coupon.domain.dto.CouponResponseDto
import com.bubaum.pairing_server.coupon.domain.dto.CouponSearchDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CouponRepositoryCustom {

    fun getCouponPage(pageable: Pageable, couponSearchDto: CouponSearchDto): Page<CouponResponseDto>

}