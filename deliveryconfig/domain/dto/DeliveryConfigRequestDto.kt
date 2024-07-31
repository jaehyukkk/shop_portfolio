package com.bubaum.pairing_server.deliveryconfig.domain.dto

import com.bubaum.pairing_server.deliveryconfig.domain.entity.DeliveryConfig
import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.DeliveryType

class DeliveryConfigRequestDto(
    val company: DeliveryCompany,
    val type: DeliveryType,
    val differentialPrices: String? = null
) {
    fun toEntity() : DeliveryConfig {
        return DeliveryConfig(
            company = company,
            type = type,
            differentialPrices = differentialPrices
        )
    }
}