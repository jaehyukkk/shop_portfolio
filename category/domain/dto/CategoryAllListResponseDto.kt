package com.bubaum.pairing_server.category.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class CategoryAllListResponseDto @QueryProjection constructor(
    val idx: Long,
    val name: String,
    val orderNumber: Int,
    val isEnabled: Boolean = true,
    val parentIdx: Long? = null,
) {
}