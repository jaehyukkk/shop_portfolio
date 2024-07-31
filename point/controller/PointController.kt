package com.bubaum.pairing_server.point.controller

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.point.service.PointService
import com.bubaum.pairing_server.user.service.UserService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api/v1/point")
@RestController
class PointController(
    private val pointService: PointService,
    private val userService: UserService
) {

    @GetMapping()
    fun getPoint(principal: Principal): ResponseEntity<Int> {
        return ResponseEntity.ok(pointService.getUserPoint(userService.getUser(principal.name.toLong())))
    }
}
