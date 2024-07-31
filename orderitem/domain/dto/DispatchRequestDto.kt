package com.bubaum.pairing_server.orderitem.domain.dto

class DispatchRequestDto(
    val dispatchItems: List<DispatchRequestItemDto>,
) {

    companion object{
        class DispatchRequestItemDto(
            val idx: Long,
            val deliveryCompanyCode: String,
            val invoiceNumber: Long,
        ) {
        }
    }
}