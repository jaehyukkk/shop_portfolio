package com.bubaum.pairing_server.reviewoptiontemplate.domain.dto

class ReviewOptionTemplateParentOptionDto(
    val name: String? = null,
    val question: String? = null,
    val reviewOptionValues: List<ReviewOptionTemplateChildOptionDto>? = null,
) {
}