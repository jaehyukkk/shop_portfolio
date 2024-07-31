package com.bubaum.pairing_server.review.controller

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.review.domain.dto.ReviewRequestDto
import com.bubaum.pairing_server.review.domain.dto.ReviewResponseDto
import com.bubaum.pairing_server.review.domain.dto.ReviewStatisticsResponseDto
import com.bubaum.pairing_server.reviewoptiongroup.service.ReviewOptionGroupService
import com.bubaum.pairing_server.review.service.ReviewService
import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/api/v1/review")
class ReviewController(
    private val reviewService: ReviewService,
    private val reviewOptionGroupService: ReviewOptionGroupService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart(value = "review") reviewRequestDto: ReviewRequestDto
        , @RequestPart(value = "images", required = false) images : List<MultipartFile>
        , principal: Principal
    ): ResponseEntity<Void> {
        reviewService.create(reviewRequestDto, images, principal.name.toLong())
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productIdx}")
    fun getList(@PathVariable productIdx: Long): ResponseEntity<List<ReviewResponseDto>> {
        return ResponseEntity.ok(
            reviewService.getList(productIdx)
        )
    }

    @GetMapping("/option-group/{productIdx}")
    fun getReviewOptionGroupList(@PathVariable productIdx : Long): ResponseEntity<List<ReviewOptionGroupListResponseDto>> {
        return ResponseEntity.ok(
            reviewOptionGroupService.getReviewOptionGroupList(productIdx))
    }

    @GetMapping("/statistics/{productIdx}")
    fun getReviewOptionStatistics(@PathVariable productIdx : Long): ResponseEntity<ReviewStatisticsResponseDto> {
        return ResponseEntity.ok(
            ReviewStatisticsResponseDto(
                reviewOptionStatistics = reviewService.getReviewOptionStatistics(productIdx),
                ratingAvg = reviewService.ratingAvg(productIdx),
                reviewPercentages = reviewService.getRatingPercentage(productIdx))
        )
    }
}
