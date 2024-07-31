package com.bubaum.pairing_server.member.domain.dto

import com.bubaum.pairing_server.member.domain.entity.Member
import com.querydsl.core.annotations.QueryProjection

data class MemberResponseDto @QueryProjection constructor(
    val idx : Long,
    val id : String,
    val roles: String,
){
    companion object{
        fun of(member : Member) : MemberResponseDto {
            return MemberResponseDto(
                idx = member.idx!!,
                id = member.user.id,
                roles = member.user.auths,
            )
        }
    }
}
