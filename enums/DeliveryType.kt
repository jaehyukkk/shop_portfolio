package com.bubaum.pairing_server.enums

enum class DeliveryType(
    val code: String
) {
    FREE("0"), DIFFERENCES_AMOUNT("1"), FIXED_AMOUNT("2")
}