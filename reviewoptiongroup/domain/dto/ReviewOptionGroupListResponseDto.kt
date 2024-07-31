package com.bubaum.pairing_server.reviewoptiongroup.domain.dto

import com.bubaum.pairing_server.reviewoptionvalue.domain.dto.ReviewOptionValueListResponseDto

class ReviewOptionGroupListResponseDto(
    val idx: Long,
    val name: String,
    val required: Boolean,
    val reviewOptionValues: MutableSet<ReviewOptionValueListResponseDto> = HashSet()
) {
}