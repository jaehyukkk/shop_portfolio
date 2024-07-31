package com.bubaum.pairing_server.productoptionvalue.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class ProductOptionValueResponseDto @QueryProjection constructor(
    val idx: Long,
    val name: String,
    val value: String,
) {
}