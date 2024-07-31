package com.bubaum.pairing_server.deliveryconfig.domain.dto

import com.bubaum.pairing_server.deliveryconfig.domain.entity.DeliveryConfig
import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.DeliveryType

class DeliveryConfigResponseDto(
    val idx: Long,
    val company: String,
    val type: String,
    var differentialPrices: List<DifferentialPriceDto>? = ArrayList()
) {

    companion object{
        fun of(deliveryConfig: DeliveryConfig, differentialPrices: List<DifferentialPriceDto>? = ArrayList()) : DeliveryConfigResponseDto {
            return DeliveryConfigResponseDto(
                idx = deliveryConfig.idx!!
                , company = deliveryConfig.company.code
                , type = deliveryConfig.type.code
                , differentialPrices=differentialPrices
            )
        }
    }
}