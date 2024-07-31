package com.bubaum.pairing_server.user.domain.dto

import com.bubaum.pairing_server.user.domain.entity.User
import io.swagger.v3.oas.annotations.media.Schema

class UserResponseDto (
    @Schema(description = "회원 IDX", defaultValue = "1")
    var idx:Long?,
    @Schema(description = "회원 ID", defaultValue = "admin")
    var id: String,
    @Schema(description = "회원 권한", defaultValue = """["MASTER","ADMIN","EMPLOYEE"]""")
    var auths:List<String>
){

    companion object{
        fun of(user: User): UserResponseDto {
            return UserResponseDto(
                idx = user.idx,
                id = user.id,
                auths=user.auths.split(",").map { it.trim() }.toList()
            )
        }
    }


}