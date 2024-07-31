package com.bubaum.pairing_server.deliveryinfo.controller

import com.bubaum.pairing_server.deliveryinfo.domain.dto.DeliveryInfoRequestDto
import com.bubaum.pairing_server.deliveryinfo.domain.entity.DeliveryInfo
import com.bubaum.pairing_server.deliveryinfo.service.DeliveryInfoService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.user.service.UserService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api/v1/delivery-info")
@RestController
class DeliveryInfoController(
    private val deliveryInfoService: DeliveryInfoService,
    private val userService: UserService
) {

    @GetMapping("/user")
    fun getUserDeliveryInfo(principal: Principal) : ResponseEntity<List<DeliveryInfo>> {
        return ResponseEntity.ok(deliveryInfoService.getUserDeliveryInfo(principal.name.toLong()))
    }

    @PostMapping()
    fun create(@RequestBody deliveryInfoRequestDto: DeliveryInfoRequestDto, principal: Principal): ResponseEntity<DeliveryInfo> {
        println(deliveryInfoRequestDto.deliveryInfoName)
        return ResponseEntity.ok(deliveryInfoService.create(deliveryInfoRequestDto, userService.getUser(principal.name.toLong())))
    }

    @PutMapping("/{idx}")
    fun modify(@PathVariable idx: Long, @RequestBody deliveryInfoRequestDto: DeliveryInfoRequestDto, principal: Principal): ResponseEntity<DeliveryInfo> {
        return ResponseEntity.ok(deliveryInfoService.modify(idx, deliveryInfoRequestDto, userService.getUser(principal.name.toLong())))
    }

    @PutMapping("/{idx}/default")
    fun modifyDefault(@PathVariable idx: Long, principal: Principal): ResponseEntity<Void> {
        deliveryInfoService.modifyDefault(idx, userService.getUser(principal.name.toLong()))
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{idx}")
    fun delete(@PathVariable idx: Long, principal: Principal): ResponseEntity<Void> {
        deliveryInfoService.delete(idx, userService.getUser(principal.name.toLong()))
        return ResponseEntity.ok().build()
    }
}
