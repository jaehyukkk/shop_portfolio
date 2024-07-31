package com.bubaum.pairing_server.user.domain.dto.oauth2


abstract class OAuth2UserInfo(protected var attributes: Map<String, Any>) {
    //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    abstract val id: String?
    abstract val nickname: String?
}
