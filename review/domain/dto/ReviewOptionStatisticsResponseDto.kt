package com.bubaum.pairing_server.review.domain.dto

class ReviewOptionStatisticsResponseDto(
    val reviewOptionGroupName: String? = null,
    val reviewOptionValues: MutableList<ReviewOption>? = null
) {
    companion object{
        class ReviewOption(
            var reviewOptionValueName: String? = null,
            var count: Long? = null,
            var percentage: Int? = null
        ){
        }
    }
}