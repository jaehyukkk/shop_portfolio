package com.bubaum.pairing_server.reviewoptiontemplate.domain.dto

import com.bubaum.pairing_server.reviewoptiontemplate.domain.entity.ReviewOptionTemplate
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ReviewOptionTemplateListResponseDto(
    val idx: Long,
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createDate: LocalDateTime? = null,
) {

    companion object{
        fun of(reviewOptionTemplate : ReviewOptionTemplate): ReviewOptionTemplateListResponseDto {
            return ReviewOptionTemplateListResponseDto(reviewOptionTemplate.idx!!, reviewOptionTemplate.name, reviewOptionTemplate.createDate)
        }
    }
}