package com.bubaum.pairing_server.product.domain.dto

import com.bubaum.pairing_server.category.domain.entity.Category
import com.bubaum.pairing_server.enums.PointStandard
import com.bubaum.pairing_server.productoptiongroup.domain.dto.ProductOptionGroupRequestDto
import com.fasterxml.jackson.annotation.JsonIgnore

class ProductModifyDto(
    val categoryIdx: Long,
    val name: String,
    val description: String,
    val pointStandard: PointStandard,
    val point: Int,
    val orderNum: Int,
    val price: Int,
    val isDisplay: Boolean,
    val isNewOptionGroup: Boolean = false,
    val productOptionGroups: List<ProductOptionGroupRequestDto>? = null,

    val removeImages: List<Long>? = null,
    val thumbnailIdx: Long? = null,
    val thumbnailIndex: Int? = null
) {
    @JsonIgnore
    var idx : Long? = null
    @JsonIgnore
    var category: Category? = null

}
