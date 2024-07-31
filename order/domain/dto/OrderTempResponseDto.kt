package com.bubaum.pairing_server.order.domain.dto

import com.bubaum.pairing_server.deliveryinfo.domain.dto.DeliveryInfoRequestDto

class OrderTempResponseDto(
    val orderTemp: OrderTempDto,
    val deliveryInfo: DeliveryInfoRequestDto? = null,
) {
}