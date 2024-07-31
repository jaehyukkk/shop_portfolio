package com.bubaum.pairing_server.admin.domain.dto

import com.bubaum.pairing_server.admin.domain.entity.Admin
import com.querydsl.core.annotations.QueryProjection

data class AdminResponseDto @QueryProjection constructor(
    val idx : Long,
    val id : String,
    val roles: String,
    val phone : String
){
    companion object{
        fun of(admin : Admin) : AdminResponseDto {
            return AdminResponseDto(
                idx = admin.idx!!,
                id = admin.user.id,
                phone = admin.phone,
                roles = admin.user.auths
            )
        }
    }
}