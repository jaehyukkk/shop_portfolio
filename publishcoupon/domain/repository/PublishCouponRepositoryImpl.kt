package com.bubaum.pairing_server.publishcoupon.domain.repository

import com.bubaum.pairing_server.coupon.domain.dto.PublishCouponResponseDto
import com.bubaum.pairing_server.coupon.domain.dto.PublishCouponSearchDto
import com.bubaum.pairing_server.coupon.domain.dto.UserCouponResponseDto
import com.bubaum.pairing_server.publishcoupon.domain.entity.QPublishCoupon.publishCoupon
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import java.time.LocalDateTime

class PublishCouponRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PublishCouponRepositoryCustom {
    override fun getUserCouponList(memberIdx: Long): List<UserCouponResponseDto> {

        return queryFactory.select(
            Projections.constructor(
                UserCouponResponseDto::class.java,
                publishCoupon.idx,
                publishCoupon.coupon.name,
                publishCoupon.coupon.expiredAt,
                publishCoupon.coupon.couponType,
                publishCoupon.coupon.discount
            )
        ).from(publishCoupon)
            .leftJoin(publishCoupon.member)
            .leftJoin(publishCoupon.coupon)
            .orderBy(publishCoupon.idx.desc())
            .where(publishCoupon.member?.idx?.eq(memberIdx)
                , publishCoupon.coupon.expiredAt?.after(LocalDateTime.now())
                , publishCoupon.isUsed?.eq(false))
            .fetch()
    }
    override fun getPublishCouponPage(
        pageable: Pageable,
        publishCouponSearchDto: PublishCouponSearchDto
    ): Page<PublishCouponResponseDto> {

        val fetch = queryFactory.select(
            Projections.constructor(
                PublishCouponResponseDto::class.java,
                publishCoupon.idx,
                publishCoupon.coupon.idx,
                publishCoupon.coupon.name,
                publishCoupon.coupon.isEnabled,
                publishCoupon.coupon.description,
                publishCoupon.coupon.expiredAt,
                publishCoupon.isUsed,
                publishCoupon.usedAt,
                publishCoupon.member.user.idx,
                publishCoupon.member.user.id,
            )
        ).from(publishCoupon)
            .leftJoin(publishCoupon.coupon)
            .leftJoin(publishCoupon.member.user)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(publishCoupon.idx.count())
            .from(publishCoupon)
            .fetchFirst()

        return PageableExecutionUtils.getPage(fetch, pageable){count}
    }
}