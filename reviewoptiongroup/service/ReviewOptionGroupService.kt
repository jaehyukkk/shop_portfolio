package com.bubaum.pairing_server.reviewoptiongroup.service

import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto
import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupRequestDto
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.reviewoptiongroup.domain.repository.ReviewOptionGroupRepository
import com.bubaum.pairing_server.reviewoptionvalue.service.ReviewOptionValueService
import org.springframework.stereotype.Service

@Service
class ReviewOptionGroupService(
    private val reviewOptionGroupRepository: ReviewOptionGroupRepository,
    private val reviewOptionValueService: ReviewOptionValueService
) {

    fun create(reviewOptionGroupRequestDto: ReviewOptionGroupRequestDto, product: Product) {
        val reviewOptionGroup = reviewOptionGroupRepository.save(reviewOptionGroupRequestDto.toEntity(product))
        for(reviewOptionValueRequestDto in reviewOptionGroupRequestDto.reviewOptionValues) {
            reviewOptionValueService.create(reviewOptionValueRequestDto, reviewOptionGroup)
        }
    }

    fun getReviewOptionGroupList(productIdx: Long) : List<ReviewOptionGroupListResponseDto> {
        return reviewOptionGroupRepository.getReviewOptionGroupList(productIdx)
    }
}