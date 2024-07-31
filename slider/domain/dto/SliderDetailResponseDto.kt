package com.bubaum.pairing_server.slider.domain.dto

import com.bubaum.pairing_server.slider.enums.SliderType
import com.querydsl.core.annotations.QueryProjection

data class SliderDetailResponseDto @QueryProjection constructor(
    val idx: Long,
    val title: String,
    val description: String? = null,
    val fileIdx: Long,
    val fileOriginalFilename: String,
    val sliderType: SliderType,
    val url: String? = null,
    val couponIdx: Long? = null,
    val couponName: String? = null
) {
}
