package com.bubaum.pairing_server.reviewoptiontemplate.service

import com.bubaum.pairing_server.deliveryconfig.domain.dto.DifferentialPriceDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateListResponseDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateParentOptionDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateRequestDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.dto.ReviewOptionTemplateResponseDto
import com.bubaum.pairing_server.reviewoptiontemplate.domain.entity.ReviewOptionTemplate
import com.bubaum.pairing_server.reviewoptiontemplate.domain.repository.ReviewOptionTemplateRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewOptionTemplateService(
    private val reviewOptionTemplateRepository: ReviewOptionTemplateRepository
) {

    @Transactional
    fun create(reviewOptionTemplateRequestDto: ReviewOptionTemplateRequestDto) : ReviewOptionTemplate{
        return reviewOptionTemplateRepository.save(reviewOptionTemplateRequestDto.toEntity())
    }

    fun detail(idx: Long) : ReviewOptionTemplateResponseDto{
        val reviewOptionTemplate = reviewOptionTemplateRepository.findById(idx).orElseThrow { Exception("존재하지 않는 리뷰 옵션 템플릿입니다.")}
        val gson = Gson()
        val listType = object : TypeToken<List<ReviewOptionTemplateParentOptionDto>>() {}.type
        val options = gson.fromJson<List<ReviewOptionTemplateParentOptionDto>>(reviewOptionTemplate.content, listType)

        return ReviewOptionTemplateResponseDto(
            idx = reviewOptionTemplate.idx!!,
            name = reviewOptionTemplate.name,
            options = options
        )

    }

    fun list() : List<ReviewOptionTemplateListResponseDto> {
        return reviewOptionTemplateRepository.findAll().stream().map{ ReviewOptionTemplateListResponseDto.of(it)}.toList()
    }
}