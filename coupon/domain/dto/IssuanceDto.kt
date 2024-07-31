package com.bubaum.pairing_server.coupon.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

class IssuanceDto(
    @Schema(description = "회원 idx", example = "52")
    val memberIdx: Long
) {
}