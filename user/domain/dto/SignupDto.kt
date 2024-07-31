package com.bubaum.pairing_server.user.domain.dto

import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.utils.global.Patterns
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Pattern


data class SignupDto (
    @Schema(description = "회원 ID", defaultValue = "admin")
    @Pattern(regexp = Patterns.ID)
    var id: String,
    @Schema(description = "패스워드", defaultValue = "1234")
    @Pattern(regexp = Patterns.PASSWORD)
    var pwd: String,
    @Schema(description = "유저권한", example = """["MASTER","ADMIN","EMPLOYEE"]""")
    val auths: List<String>

){
    fun toEntity() : User {
        val authsString = auths.joinToString(",")
        return User(
            id = id,
            pwd = pwd,
            auths = authsString
        )
    }

}
