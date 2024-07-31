package com.bubaum.pairing_server.product.domain.dto

import com.bubaum.pairing_server.enums.PointStandard
import com.bubaum.pairing_server.file.domain.dto.FileResponseDto
import com.bubaum.pairing_server.productoptiongroup.domain.dto.ProductOptionGroupResponseDto
import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto
import com.querydsl.core.annotations.QueryProjection

data class ProductModifyResponseDto @QueryProjection constructor(
    val idx: Long,
    val name: String,
    val price: Int,
    val categoryName: String,
    val categoryIdx: Long,
    val isSoldOut: Boolean,
    val description: String,
    val images: List<FileResponseDto>? = null
) {
    var optionGroups: List<ProductOptionGroupResponseDto>? = null
    var reviewOptionGroups: List<ReviewOptionGroupListResponseDto>? = null
}
