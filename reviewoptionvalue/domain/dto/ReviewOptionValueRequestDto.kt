package com.bubaum.pairing_server.reviewoptionvalue.domain.dto

import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.ReviewOptionGroup
import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.ReviewOptionValue

class ReviewOptionValueRequestDto(
    val name: String,
) {

    fun toEntity(reviewOptionGroup: ReviewOptionGroup): ReviewOptionValue {
        return ReviewOptionValue(
            name = name,
            reviewOptionGroup=reviewOptionGroup
        )
    }
}