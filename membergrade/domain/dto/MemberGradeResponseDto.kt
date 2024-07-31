package com.bubaum.pairing_server.membergrade.domain.dto

import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade

class MemberGradeResponseDto(
    val idx: Long,
    val name : String,
    val discountRate : Int,
    val isDefault : Boolean,
    val isEnabled : Boolean,
    val pointRate : Int,
    val standard : Int
) {

    companion object{
        fun of(memberRank: MemberGrade) : MemberGradeResponseDto{
            return MemberGradeResponseDto(
                idx = memberRank.idx!!,
                name = memberRank.name,
                discountRate = memberRank.discountRate,
                isDefault = memberRank.isDefault,
                isEnabled = memberRank.isEnabled,
                pointRate = memberRank.pointRate,
                standard = memberRank.standard
            )
        }
    }
}
