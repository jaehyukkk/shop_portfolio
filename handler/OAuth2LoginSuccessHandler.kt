package com.bubaum.pairing_server.handler

import com.bubaum.pairing_server.global.dto.TokenDto
import com.bubaum.pairing_server.user.domain.dto.oauth2.CustomOAuth2User
import com.bubaum.pairing_server.user.domain.dto.oauth2.OAuth2Attributes
import com.bubaum.pairing_server.user.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2LoginSuccessHandler(
    private val userService : UserService,
    @Value("\${front.url}") val frontUrl: String,

    ) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oAuth2User: CustomOAuth2User = authentication!!.principal as CustomOAuth2User
        val extractAttributes: OAuth2Attributes =
            OAuth2Attributes.of(oAuth2User.socialType, oAuth2User.nameAttributeKey, oAuth2User.attributes)
        val user = userService.selectSocialUser(oAuth2User.socialType, extractAttributes.oAuth2UserInfo.id!!)
        val tokenDto : TokenDto = userService.socialLogin(user)
        resultRedirectStrategy(request, response, tokenDto)
    }

    @Throws(IOException::class, ServletException::class)
    protected fun resultRedirectStrategy(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        tokenDto: TokenDto
    ) {
        val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()
        val uri = UriComponentsBuilder
            .fromUriString(frontUrl)
            .path("/social/redirect")
            .queryParam("accessToken", tokenDto.accessToken)
            .queryParam("refreshToken", tokenDto.refreshToken)
            .queryParam("accessTokenExpiresIn", tokenDto.accessTokenExpiresIn)
            .queryParam("refreshTokenExpiresIn", tokenDto.refreshTokenExpiresIn)
            .build()

        redirectStrategy.sendRedirect(request, response, uri.toUriString())
    }
}
