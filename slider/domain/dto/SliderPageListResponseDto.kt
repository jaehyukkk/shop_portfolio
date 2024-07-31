package com.bubaum.pairing_server.slider.domain.dto

import com.bubaum.pairing_server.slider.enums.SliderType
import com.querydsl.core.annotations.QueryProjection

data class SliderPageListResponseDto @QueryProjection constructor(
    val idx: Long,
    val title: String,
    val sliderType: SliderType,
    val couponName: String? = null,
    val orderNum: Int? = 0,
    val status: Boolean? = true
) {
}
