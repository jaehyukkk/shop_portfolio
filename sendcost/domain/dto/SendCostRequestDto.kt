package com.bubaum.pairing_server.sendcost.domain.dto

import com.bubaum.pairing_server.sendcost.domain.entity.SendCost

class SendCostRequestDto(
    val name: String,
    val price: Int,
    val startPostCode: String,
    val endPostCode: String,
    val isEnabled: Boolean? = true,
) {

    fun toEntity() : SendCost{
        return SendCost(
            name = name,
            price = price,
            startPostCode = startPostCode,
            endPostCode = endPostCode,
            isEnabled = isEnabled
        )
    }
}