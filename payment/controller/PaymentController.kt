package com.bubaum.pairing_server.payment.controller

import com.bubaum.pairing_server.payment.service.PaymentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService
) {

    @GetMapping()
    fun list() = paymentService.list()
}