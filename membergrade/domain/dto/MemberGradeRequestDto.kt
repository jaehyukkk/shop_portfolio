package com.bubaum.pairing_server.membergrade.domain.dto

import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade
import io.swagger.v3.oas.annotations.media.Schema

class MemberGradeRequestDto(
    @Schema(description = "회원 등급 이름", example = "VIP")
    val name : String,
    @Schema(description = "할인율", example = "10")
    val discountRate : Int,
    @Schema(description = "기본 등급 여부", example = "true")
    val isDefault : Boolean,
    @Schema(description = "사용 여부", example = "true")
    val isEnabled : Boolean,
    @Schema(description = "포인트 적립률", example = "10")
    val pointRate : Int,
    @Schema(description = "기준 (원 이상)", example = "100000")
    val standard : Int
) {

    fun toEntity() : MemberGrade{
        return MemberGrade(
            name = name,
            discountRate = discountRate,
            isDefault = isDefault,
            isEnabled = isEnabled,
            pointRate = pointRate,
            standard = standard
        )
    }
}
