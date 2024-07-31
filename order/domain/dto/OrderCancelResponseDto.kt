package com.bubaum.pairing_server.order.domain.dto

class OrderCancelResponseDto(
    val orderItemIdx: Long,
    val cancelReason: String,
) {
}