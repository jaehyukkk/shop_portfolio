package com.bubaum.pairing_server.cartitem.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class CartItemResponseDto @QueryProjection constructor(
    val idx: Long,
    val count: Int,
    val productIdx: Long,
    val productName: String,
    val productPrice: Int,
    val productThumbnail: Long,
    val totalPrice: Int,
    val optionPrice: Int,
    val options: String
//    val options : Set<OptionDto> = HashSet()
//    val options: Set<Options> = HashSet()
) {
}