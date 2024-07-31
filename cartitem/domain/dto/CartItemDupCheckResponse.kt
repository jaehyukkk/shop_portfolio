package com.bubaum.pairing_server.cartitem.domain.dto

class CartItemDupCheckResponse(
    val isDuplicate: Boolean,
    val cartIdx: Long? = null,
) {

}