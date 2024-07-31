package com.bubaum.pairing_server.cartitem.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class CartItemTotalPriceResponseDto @QueryProjection constructor(
    val totalPrice: Int,
) {
}