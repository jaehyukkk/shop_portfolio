package com.bubaum.pairing_server.orderitem.domain.dto

import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.OrderStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class DashboardDeliveryManagementListDto @QueryProjection constructor(
    val orderItemIdx : Long,
    val orderIdx: Long,
    val status: OrderStatus,
    val orderId : String,
    val productName : String,
    val productOptions: String,
    val count: Int,
    val price: Int,
    var deliveryCompany: DeliveryCompany? = null,
    val invoiceNumber: Long? = null,
    val receiverName: String,
    val addr: String,
    val detailAddr: String,
    val phone: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDate: LocalDateTime,
) {
    var deliveryCompanyCode: String? = null
    init{
        this.deliveryCompanyCode = deliveryCompany?.code
    }
}