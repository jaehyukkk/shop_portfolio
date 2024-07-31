package com.bubaum.pairing_server.coupon.controller

import com.bubaum.pairing_server.coupon.domain.dto.*
import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.coupon.service.CouponService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1")
class CouponController(
    private val couponService: CouponService,
) {

    @Operation(summary = "쿠폰 생성 API")
    @PostMapping("/coupon")
    fun create(@RequestBody couponRequestDto: CouponRequestDto): ResponseEntity<Coupon> {
        return ResponseEntity.ok(couponService.create(couponRequestDto))
    }

    @Operation(summary = "쿠폰 조회 API")
    @GetMapping("/coupon")
    fun getCouponList(pageDto: PageDto, couponSearchDto: CouponSearchDto): ResponseEntity<Page<CouponResponseDto>> {
        return ResponseEntity.ok(couponService.getCouponPage(pageDto, couponSearchDto))
    }
    @Operation(summary = "쿠폰 발행 API")
    @PostMapping("/publish-coupon")
    fun publish(@RequestBody publishCouponRequestDto: PublishCouponRequestDto): ResponseEntity<Void> {
        couponService.publish(publishCouponRequestDto)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "발행된 쿠폰 조회 API")
    @GetMapping("/publish-coupon")
    fun getPublishCoupon(pageDto: PageDto, publishCouponSearchDto: PublishCouponSearchDto): ResponseEntity<Page<PublishCouponResponseDto>> {
        return ResponseEntity.ok(couponService.getPublishCouponPage(pageDto, publishCouponSearchDto))
    }
    @Operation(summary = "유저에게 쿠폰 발권 API")
    @PutMapping("/publish-coupon/{publishCouponIdx}/issuance")
    fun issue(@PathVariable publishCouponIdx: Long, @RequestBody issuanceDto: IssuanceDto): ResponseEntity<Void> {
        couponService.issue(publishCouponIdx, issuanceDto.memberIdx)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "유저 쿠폰 조회 API")
    @GetMapping("/publish-coupon-user")
    fun getUserCouponList(principal: Principal): ResponseEntity<List<UserCouponResponseDto>> {
        return ResponseEntity.ok(couponService.getUserCoupon(principal.name.toLong()))

    }
}
