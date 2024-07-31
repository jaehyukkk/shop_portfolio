package com.bubaum.pairing_server.reviewoptiontemplate.domain.dto

class ReviewOptionTemplateResponseDto(
    val idx: Long,
    val name: String,
    val options: List<ReviewOptionTemplateParentOptionDto>,
) {
}