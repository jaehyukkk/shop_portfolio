package com.bubaum.pairing_server.review.domain.dto

import com.bubaum.pairing_server.file.domain.dto.FileResponseDto
import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class ReviewResponseDto @QueryProjection constructor(
    val idx: Long,
    val content: String,
    val rating: Int,
    val memberIdx: Long,
    val memberId: String,
    val orderItemOption: String,
    val reviewOption: String,
//    val reviewOptionValue: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDate: LocalDateTime,
    val images: MutableSet<FileResponseDto> = HashSet()
) {

}