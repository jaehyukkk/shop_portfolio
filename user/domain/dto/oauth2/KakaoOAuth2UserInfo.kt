package com.bubaum.pairing_server.user.domain.dto.oauth2


class KakaoOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["id"].toString()
    override val nickname: String?
        get() {
            val account = attributes["kakao_account"] as Map<*, *>?
            val profile = account?.get("profile") as Map<*, *>?
            return if (profile == null) null else profile["nickname"] as String?
        }
}
