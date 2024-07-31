package com.bubaum.pairing_server.publishcoupon.domain.repository

import com.bubaum.pairing_server.coupon.domain.dto.PublishCouponResponseDto
import com.bubaum.pairing_server.coupon.domain.dto.PublishCouponSearchDto
import com.bubaum.pairing_server.coupon.domain.dto.UserCouponResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PublishCouponRepositoryCustom {

    fun getUserCouponList(memberIdx: Long): List<UserCouponResponseDto>
    fun getPublishCouponPage(pageable: Pageable, publishCouponSearchDto: PublishCouponSearchDto): Page<PublishCouponResponseDto>


}