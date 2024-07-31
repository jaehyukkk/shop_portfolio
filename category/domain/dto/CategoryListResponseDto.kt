package com.bubaum.pairing_server.category.domain.dto

import com.bubaum.pairing_server.category.domain.entity.Category

class CategoryListResponseDto(
    val idx: Long,
    val name: String,
//    val orderNumber: Int,
//    val isEnabled: Boolean = true,
    val childrenCategory: List<ChildrenCategoryListResponseDto>? = null
//    val parentIdx: Long? = null,
//    val isChild: Boolean
) {
}