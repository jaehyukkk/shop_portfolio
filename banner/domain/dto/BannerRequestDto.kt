package com.bubaum.pairing_server.banner.domain.dto

import com.bubaum.pairing_server.banner.domain.entity.Banner

class BannerRequestDto(
    val title: String,
    val content: String,
    val isEnabled: Boolean? = true,
    val orderNumber: Int? = 9999
) {
    fun toEntity() : Banner{
        return Banner(
            title = title,
            content = content,
            isEnabled = isEnabled,
            orderNumber = orderNumber!!
        )
    }
}