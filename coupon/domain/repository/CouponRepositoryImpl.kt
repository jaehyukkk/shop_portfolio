package com.bubaum.pairing_server.coupon.domain.repository

import com.bubaum.pairing_server.coupon.domain.dto.CouponResponseDto
import com.bubaum.pairing_server.coupon.domain.dto.CouponSearchDto
import com.bubaum.pairing_server.coupon.domain.entity.QCoupon
import com.bubaum.pairing_server.publishcoupon.domain.entity.QPublishCoupon
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CouponRepositoryCustom {

    override fun getCouponPage(pageable: Pageable, couponSearchDto: CouponSearchDto): Page<CouponResponseDto> {

        val fetch = queryFactory.select(
            Projections.constructor(
                CouponResponseDto::class.java,
                QCoupon.coupon.idx,
                QCoupon.coupon.name,
                QCoupon.coupon.description,
                QCoupon.coupon.isEnabled,
                QCoupon.coupon.couponType,
                QCoupon.coupon.discount,
                JPAExpressions.select(QPublishCoupon.publishCoupon.idx.count())
                    .from(QPublishCoupon.publishCoupon)
                    .where(QPublishCoupon.publishCoupon.coupon.idx.eq(QCoupon.coupon.idx))),
        ).from(QCoupon.coupon)
            .orderBy(QCoupon.coupon.idx.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(QCoupon.coupon.idx.count())
            .from(QCoupon.coupon)
            .fetchFirst()

        return PageableExecutionUtils.getPage(fetch, pageable){count}
    }
}