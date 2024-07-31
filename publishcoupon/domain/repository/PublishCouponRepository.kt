package com.bubaum.pairing_server.publishcoupon.domain.repository

import com.bubaum.pairing_server.member.domain.entity.Member
import com.bubaum.pairing_server.publishcoupon.domain.entity.PublishCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
interface PublishCouponRepository : JpaRepository<PublishCoupon, Long>, PublishCouponRepositoryCustom {

    fun existsByIdxAndMemberNotNull(idx: Long) : Boolean
    @Transactional
    @Modifying
    @Query("update PublishCoupon a set a.member = :member where a.idx = :idx")
    fun issueCoupon(member: Member, idx: Long) : Int

    @Transactional
    @Modifying
    @Query("update PublishCoupon a set a.isUsed = true, a.usedAt = :now where a.idx = :idx")
    fun applyCoupon(now: LocalDateTime, idx: Long) : Int
}