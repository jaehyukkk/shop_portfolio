package com.bubaum.pairing_server.review.domain.dto

class ReviewStatisticsResponseDto(
    val reviewOptionStatistics : List<ReviewOptionStatisticsResponseDto>? = ArrayList(),
    val ratingAvg :Double? = null,
    val reviewPercentages: List<ReviewPercentageResponseDto>? = ArrayList()
) {
}