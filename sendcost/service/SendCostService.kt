package com.bubaum.pairing_server.sendcost.service

import com.bubaum.pairing_server.sendcost.domain.dto.SendCostRequestDto
import com.bubaum.pairing_server.sendcost.domain.entity.SendCost
import com.bubaum.pairing_server.sendcost.domain.repository.SendCostRepository
import org.springframework.stereotype.Service

@Service
class SendCostService(
    private val sendCostRepository: SendCostRepository
) {

    fun createSendCost(requestSendCostRequestDto: SendCostRequestDto) : SendCost{
        return sendCostRepository.save(requestSendCostRequestDto.toEntity())
    }

    fun getSendCostList() : List<SendCost> {
        return sendCostRepository.findByOrderByIdxDesc()
    }

    fun calculateShippingCost(postCode : Int) : Int {
        return sendCostRepository.calculateShippingCost(postCode)?.price ?: 0
    }
}