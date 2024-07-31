package com.bubaum.pairing_server.user.domain.dto

import com.bubaum.pairing_server.global.dto.TokenDto

class LoginResponseDto<T> (
    val userResponseDto: T,
    val tokenDto: TokenDto,
){
}