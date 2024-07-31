package com.bubaum.pairing_server.order.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class OrderListResponseDto @QueryProjection constructor(
    val idx: Long,
    val address: String,
    val detailAddress: String,
    //주문 시퀀스 id
    val orderIdx : Long,
    //주문번호
    val orderId: String,
    val count: Int,
    val productIdx: Long,
    val productName: String,
    val productPrice: Int,
    val productThumbnail: Long,
    val optionIdx: Long,
    val optionName: String,
    val childOptionIdx: Long,
    val childOptionName: String,
    val optionPrice: Int,
    val totalPrice: Int,
//    val items: MutableSet<CartItemResponseDto>
) {
}