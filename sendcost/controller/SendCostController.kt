package com.bubaum.pairing_server.sendcost.controller

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.sendcost.domain.dto.SendCostRequestDto
import com.bubaum.pairing_server.sendcost.domain.entity.SendCost
import com.bubaum.pairing_server.sendcost.service.SendCostService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/sendcost")
@RestController
class SendCostController(
    private val sendCostService: SendCostService
) {

    @GetMapping()
    fun getSendCostList() : ResponseEntity<List<SendCost>> {
        return ResponseEntity.ok(sendCostService.getSendCostList())
    }
    @PostMapping()
    fun create(@RequestBody sendCostRequestDto: SendCostRequestDto): ResponseEntity<SendCost> {
        return ResponseEntity.ok(sendCostService.createSendCost(sendCostRequestDto))
    }
}
