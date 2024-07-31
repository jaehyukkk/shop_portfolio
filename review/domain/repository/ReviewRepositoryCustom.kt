package com.bubaum.pairing_server.review.domain.repository

import com.bubaum.pairing_server.review.domain.dto.ReviewOptionStatisticsQuerydslDto
import com.bubaum.pairing_server.review.domain.dto.ReviewResponseDto

interface ReviewRepositoryCustom {

    fun getReviewList(productIdx: Long) : List<ReviewResponseDto>

    fun getReviewOptionStatistics(productIdx: Long) : List<ReviewOptionStatisticsQuerydslDto>

    fun getRatingList(productIdx: Long) : Double
}