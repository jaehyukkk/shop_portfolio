package com.bubaum.pairing_server.order.domain.dto

import com.bubaum.pairing_server.deliveryinfo.domain.dto.DeliveryInfoRequestDto

class OrderTempDto(
    val orderProducts: List<OrderProduct>? = null,
    val orderId: String? = null,
    val amount: Int? = null,
    var userIdx: Long? = null,
    val deliveryInfoIdx: Long? = null,
    val deliveryInfoRequestDto: DeliveryInfoRequestDto? = null,
    val point: Int? = null,
) {
    companion object{
        class OrderProduct(
            val cartItemIdx: Long? = null,
            val couponIdx: Long? = null,
            val useCoupon: Boolean? = false,
        ){
        }
    }
}