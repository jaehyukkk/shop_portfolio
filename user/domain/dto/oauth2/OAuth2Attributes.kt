package com.bubaum.pairing_server.user.domain.dto.oauth2

import com.bubaum.pairing_server.enums.SocialType

class OAuth2Attributes(
    var nameAttributeKey: String,
    var oAuth2UserInfo: OAuth2UserInfo
) {

    companion object{
        fun of(socialType: SocialType, userNameAttributeName: String, attributes: Map<String, Any>): OAuth2Attributes {
            return ofKakao(userNameAttributeName, attributes)
        }

        private fun ofKakao(userNameAttributeName: String, attributes: Map<String, Any>): OAuth2Attributes {
            return OAuth2Attributes(userNameAttributeName, KakaoOAuth2UserInfo(attributes))
        }
    }

}