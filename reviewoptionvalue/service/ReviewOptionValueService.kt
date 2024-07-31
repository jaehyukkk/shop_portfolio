package com.bubaum.pairing_server.reviewoptionvalue.service

import com.bubaum.pairing_server.reviewoptionvalue.domain.dto.ReviewOptionValueRequestDto
import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.ReviewOptionGroup
import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.ReviewOptionValue
import com.bubaum.pairing_server.reviewoptionvalue.domain.repository.ReviewOptionsValueRepository
import org.springframework.stereotype.Service

@Service
class ReviewOptionValueService(
    private val reviewOptionsValueRepository: ReviewOptionsValueRepository
) {

    fun create(reviewOptionValueRequestDto: ReviewOptionValueRequestDto, reviewOptionGroup: ReviewOptionGroup) {
        reviewOptionsValueRepository.save(reviewOptionValueRequestDto.toEntity(reviewOptionGroup))
    }

    fun getReviewOptionValue(idx: Long): ReviewOptionValue {
        return reviewOptionsValueRepository.findById(idx).orElseThrow()
    }
}