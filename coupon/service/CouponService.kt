package com.bubaum.pairing_server.coupon.service

import com.bubaum.pairing_server.coupon.domain.dto.*
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.publishcoupon.domain.entity.PublishCoupon
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.member.service.MemberService
import com.bubaum.pairing_server.coupon.domain.repository.CouponRepository
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.publishcoupon.domain.repository.PublishCouponRepository
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val publishCouponRepository: PublishCouponRepository,
    private val memberService: MemberService
) {
    fun create(couponRequestDto: CouponRequestDto) : Coupon {
        return couponRepository.save(couponRequestDto.toEntity())
    }

    fun publish(publishCouponRequestDto: PublishCouponRequestDto) {
        val coupon = couponRepository.findById(publishCouponRequestDto.couponIdx).orElseThrow()
        for(i in 1..publishCouponRequestDto.count){
            publishCouponRepository.save(publishCouponRequestDto.toEntity(coupon))
        }
    }

    fun issue(publishCouponIdx: Long, memberIdx: Long) {
        val member = memberService.getMember(memberIdx)
        if(publishCouponRepository.existsByIdxAndMemberNotNull(publishCouponIdx)) throw BaseException(ErrorCode.NOT_FOUND_ORDER)
        if(publishCouponRepository.issueCoupon(member, publishCouponIdx) != 1) throw BaseException(ErrorCode.NOT_FOUND_ORDER)
    }

    fun getUserCoupon(memberIdx: Long) : List<UserCouponResponseDto>{
        return publishCouponRepository.getUserCouponList(memberIdx)
    }

    fun getCouponPage(pageDto: PageDto, couponSearchDto: CouponSearchDto) : Page<CouponResponseDto>{
        return couponRepository.getCouponPage(pageDto.pageable(), couponSearchDto)
    }

    fun getPublishCouponPage(pageDto: PageDto, publishCouponSearchDto: PublishCouponSearchDto) : Page<PublishCouponResponseDto>{
        return publishCouponRepository.getPublishCouponPage(pageDto.pageable(), publishCouponSearchDto)
    }

    fun getPublishCoupon(publishCouponIdx: Long) : PublishCoupon {
        return publishCouponRepository.findById(publishCouponIdx).orElseThrow()
    }

    fun applyPublishCoupon(publishCouponIdx: Long) {
        if (publishCouponRepository.applyCoupon(LocalDateTime.now(), publishCouponIdx) != 1) throw BaseException(ErrorCode.NOT_FOUND_ORDER)
    }

    fun getCoupon(couponIdx: Long) : Coupon {
        return couponRepository.findById(couponIdx).orElseThrow {
            BaseException(ErrorCode.NOT_FOUND_ORDER)
        }
    }
}
