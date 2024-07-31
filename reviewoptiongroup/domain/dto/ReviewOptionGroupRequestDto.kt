package com.bubaum.pairing_server.reviewoptiongroup.domain.dto

import com.bubaum.pairing_server.reviewoptionvalue.domain.dto.ReviewOptionValueRequestDto
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.ReviewOptionGroup
import io.swagger.v3.oas.annotations.media.Schema

class ReviewOptionGroupRequestDto(
    @Schema(description = "리뷰옵션명", defaultValue = "사이즈")
    val name: String,
    @Schema(description = "리뷰를 작성할때 필수로 기입해야할지 여부", defaultValue = "true")
    val required: Boolean,
    val reviewOptionValues: List<ReviewOptionValueRequestDto>
) {

    fun toEntity(product: Product): ReviewOptionGroup {
        return ReviewOptionGroup(
            name = name,
            required = required,
            product = product,
        )
    }
}