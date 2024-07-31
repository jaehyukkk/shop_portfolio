package com.bubaum.pairing_server.cartitem.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class CartItemDupCheckDto @QueryProjection constructor(
    val cartIdx: Long,
    val optionsIdxList: List<IdxsDto>
) {

    companion object {
        class IdxsDto(
            val idx: Long
        )
    }
}