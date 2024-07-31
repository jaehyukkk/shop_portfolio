package com.bubaum.pairing_server.productoptiongroup.domain.dto

import com.bubaum.pairing_server.productoptionvalue.domain.dto.ProductOptionValueResponseDto
import com.querydsl.core.annotations.QueryProjection

data class ProductOptionGroupResponseDto @QueryProjection constructor(
    val idx: Long,
    val stock: Int,
    val addPrice: Int,
    val productOptionValues: MutableSet<ProductOptionValueResponseDto> = HashSet()
) {
}