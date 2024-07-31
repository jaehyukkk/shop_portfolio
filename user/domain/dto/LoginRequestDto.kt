package com.bubaum.pairing_server.user.domain.dto

import com.bubaum.pairing_server.enums.Role
import com.bubaum.pairing_server.utils.global.Patterns
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.validation.constraints.Pattern


open class LoginRequestDto (
    @Schema(description = "회원 ID", defaultValue = "admin")
    @Pattern(regexp = Patterns.ID)
    private var id : String,
    @Schema(description = "회원 비밀번호", defaultValue = "1234")
    @Pattern(regexp = Patterns.PASSWORD)
    private var pwd : String,
//    @Schema(description = "권한", defaultValue = "ROLE_ADMIN")
//    val role : Role,
){

    fun toAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(id,pwd)
    }
}
