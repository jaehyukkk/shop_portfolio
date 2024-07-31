package com.bubaum.pairing_server.banner.controller

import com.bubaum.pairing_server.banner.domain.dto.BannerRequestDto
import com.bubaum.pairing_server.banner.domain.entity.Banner
import com.bubaum.pairing_server.banner.service.BannerService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/banner")
@RestController
class BannerController(
    private val bannerService: BannerService
) {

    @PostMapping()
    fun create(@RequestBody bannerRequestDto: BannerRequestDto): ResponseEntity<Banner> {
        return ResponseEntity.ok(bannerService.create(bannerRequestDto))
    }

    @GetMapping()
    fun getBannerList() : ResponseEntity<List<Banner>>{
        return ResponseEntity.ok(bannerService.getBannerList())
    }
}
