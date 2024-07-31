package com.bubaum.pairing_server.slider.domain.dto

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.slider.domain.entity.Slider
import com.bubaum.pairing_server.slider.enums.SliderType
import io.swagger.v3.oas.annotations.media.Schema

class SliderRequestDto(
    @Schema(description = "슬라이더 제목", defaultValue = "슬라이더1")
    val title: String,
    @Schema(description = "슬라이더 설명", defaultValue = "슬라이더 설명입니다.")
    val description: String? = null,
    @Schema(description = "슬라이더 타입", defaultValue = "BLANK_URL")
    val sliderType: SliderType,
    @Schema(description = "슬라이더 클릭시 이동 URL", defaultValue = "/")
    val url: String? = null,
    @Schema(description = "쿠폰 idx", defaultValue = "1")
    val couponIdx: Long? = null,
    @Schema(description = "순서", defaultValue = "0")
    val orderNum: Int = 0,
    @Schema(description = "활성상태", defaultValue = "true")
    val status: Boolean = true,
) {

    fun toEntity(file : File, coupon: Coupon?) : Slider {
        return Slider(
            title = title,
            description = description,
            file = file,
            sliderType = sliderType,
            url = url,
            coupon = coupon,
            orderNum = orderNum,
            status = status
        )
    }
}
