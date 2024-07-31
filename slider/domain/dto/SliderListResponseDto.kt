package com.bubaum.pairing_server.slider.domain.dto

import com.bubaum.pairing_server.slider.enums.SliderType
import com.querydsl.core.annotations.QueryProjection

data class SliderListResponseDto @QueryProjection constructor(
    val idx : Long,
    val fileIdx : Long,
    val sliderType: SliderType,
    val url : String? = null,
    val couponIdx : Long? = null
) {
}
