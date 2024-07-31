package com.bubaum.pairing_server.global.dto

class TokenDto (
    val grantType: String,
    val accessToken: String,
    val refreshToken: String? = null,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long? = null
){

}
