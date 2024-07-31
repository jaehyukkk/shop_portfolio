package com.bubaum.pairing_server.payment.service

import com.bubaum.pairing_server.payment.domain.dto.PaymentResponseDto
import com.bubaum.pairing_server.payment.domain.repository.payment.payment.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    fun list() : List<PaymentResponseDto>{
        return paymentRepository.findAll().map { PaymentResponseDto.of(it) }
    }
}