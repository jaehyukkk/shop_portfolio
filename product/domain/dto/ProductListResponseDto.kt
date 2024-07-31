package com.bubaum.pairing_server.product.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class ProductListResponseDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val price: Int,
    val orderCount: Long,
    val categoryName: String,
    val categoryIdx: Long,
    val thumbnailIdx: Long,
    val isSoldOut: Boolean,
){
}