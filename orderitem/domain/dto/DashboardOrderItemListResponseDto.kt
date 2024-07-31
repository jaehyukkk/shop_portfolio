package com.bubaum.pairing_server.orderitem.domain.dto

import com.bubaum.pairing_server.enums.OrderStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import java.math.BigDecimal
import java.time.LocalDateTime

data class DashboardOrderItemListResponseDto @QueryProjection constructor(
    val orderItemIdx : Long,
    val orderIdx: Long,
    val status: OrderStatus,
    val orderId : String,
    val productName : String,
    val productOptions: String,
    val count: Int,
    val price: Int,
    val couponName: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDate: LocalDateTime,
    ) {
}