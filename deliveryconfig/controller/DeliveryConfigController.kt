package com.bubaum.pairing_server.deliveryconfig.controller

import com.bubaum.pairing_server.deliveryconfig.domain.dto.DeliveryConfigRequestDto
import com.bubaum.pairing_server.deliveryconfig.domain.dto.DeliveryConfigResponseDto
import com.bubaum.pairing_server.deliveryconfig.domain.entity.DeliveryConfig
import com.bubaum.pairing_server.deliveryconfig.service.DeliveryConfigService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/delivery-config")
@RestController
class DeliveryConfigController(
    private val deliveryConfigService: DeliveryConfigService
) {

    @PostMapping()
    fun create(@RequestBody deliveryConfigRequestDto: DeliveryConfigRequestDto): ResponseEntity<Void> {
        deliveryConfigService.create(deliveryConfigRequestDto)
        return ResponseEntity.ok().build()
    }

    @GetMapping()
    fun get(): ResponseEntity<DeliveryConfigResponseDto> {
        return ResponseEntity.ok(
            deliveryConfigService.get()
        )
    }
}
