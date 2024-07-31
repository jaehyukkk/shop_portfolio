package com.bubaum.pairing_server.reviewoptiontemplate.controller

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateListResponseDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateRequestDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateResponseDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.entity.ReviewOptionTemplate
import com.bubaum.pairing_server.reviewoptiontemplate.service.ReviewOptionTemplateService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/review-option-template")
@RestController
class ReviewOptionTemplateController(
    private val reviewOptionTemplateService: ReviewOptionTemplateService
) {

    @PostMapping()
    fun create(@RequestBody reviewOptionTemplateRequestDto: ReviewOptionTemplateRequestDto): ResponseEntity<ReviewOptionTemplate> {
        return ResponseEntity.ok(
            reviewOptionTemplateService.create(reviewOptionTemplateRequestDto)
        )
    }

    @GetMapping("{idx}")
    fun detail(@PathVariable idx: Long): ResponseEntity<ReviewOptionTemplateResponseDto> {
        return ResponseEntity.ok(
            reviewOptionTemplateService.detail(idx)
        )
    }

    @GetMapping()
    fun list(): ResponseEntity<List<ReviewOptionTemplateListResponseDto>> {
        return ResponseEntity.ok(
            reviewOptionTemplateService.list()
        )
    }

}
