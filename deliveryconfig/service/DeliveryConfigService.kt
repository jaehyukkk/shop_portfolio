package com.bubaum.pairing_server.deliveryconfig.service

import com.bubaum.pairing_server.deliveryconfig.domain.dto.DeliveryConfigRequestDto
import com.bubaum.pairing_server.deliveryconfig.domain.dto.DeliveryConfigResponseDto
import com.bubaum.pairing_server.deliveryconfig.domain.dto.DifferentialPriceDto
import com.bubaum.pairing_server.deliveryconfig.domain.repository.DeliveryConfigRepository
import com.bubaum.pairing_server.enums.DeliveryType
import com.bubaum.pairing_server.utils.global.GsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeliveryConfigService(
    private val deliveryConfigRepository: DeliveryConfigRepository
) {

    @Transactional
    fun create(deliveryConfigRequestDto: DeliveryConfigRequestDto) {
        deliveryConfigRepository.deleteAll()
        deliveryConfigRepository.save(deliveryConfigRequestDto.toEntity())
    }

    fun get() : DeliveryConfigResponseDto? {
        val deliveryConfig = deliveryConfigRepository.findFirstByIdxNotNull()
        deliveryConfig ?: return null
        if(deliveryConfig.type != DeliveryType.DIFFERENCES_AMOUNT) return DeliveryConfigResponseDto.of(deliveryConfig)
        val gson = Gson()
        val listType = object : TypeToken<List<DifferentialPriceDto>>() {}.type
        val differentialPrices = gson.fromJson<List<DifferentialPriceDto>>(deliveryConfig.differentialPrices, listType)
        return DeliveryConfigResponseDto.of(deliveryConfig, differentialPrices)
    }
}
