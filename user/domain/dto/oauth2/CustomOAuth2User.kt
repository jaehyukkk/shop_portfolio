package com.bubaum.pairing_server.user.domain.dto.oauth2

import com.bubaum.pairing_server.enums.SocialType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User


class CustomOAuth2User(
    authorities: Collection<GrantedAuthority>,
    attributes: Map<String, Any>,
    val nameAttributeKey: String,
    private val auths: String,
    val socialType: SocialType
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey) {

}
