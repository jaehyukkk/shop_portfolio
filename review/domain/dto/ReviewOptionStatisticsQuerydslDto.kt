package com.bubaum.pairing_server.review.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class ReviewOptionStatisticsQuerydslDto @QueryProjection constructor(
    val reviewOption: String,
    val reviewOptionValue: String,
    val count: Long
) {
}