package com.bubaum.pairing_server.reviewoptiongroup.domain.repository

import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto

interface ReviewOptionGroupRepositoryCustom {

    fun getReviewOptionGroupList(productIdx: Long) : List<ReviewOptionGroupListResponseDto>
}