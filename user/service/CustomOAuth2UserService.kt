package com.bubaum.pairing_server.user.service
import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.member.domain.dto.MemberSignupDto
import com.bubaum.pairing_server.user.domain.dto.oauth2.CustomOAuth2User
import com.bubaum.pairing_server.user.domain.dto.oauth2.OAuth2Attributes
import com.bubaum.pairing_server.user.domain.dto.oauth2.OAuth2UserInfo
import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.member.service.MemberService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomOAuth2UserService(
    private val memberService: MemberService
) : OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {

        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        //소셜 타입 구분(kakao, naver, google ...)
        val registrationId = userRequest!!.clientRegistration.registrationId
        val socialType = getSocialType(registrationId)
        // OAuth2 로그인 시 키(PK)가 되는 값
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails.userInfoEndpoint.userNameAttributeName
        // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)
        val attributes = oAuth2User.attributes


        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        val extractAttributes: OAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes)
        val user = getSocialUser(extractAttributes, socialType)
        return CustomOAuth2User(
            setOf(SimpleGrantedAuthority(user.roles)),
            attributes,
            extractAttributes.nameAttributeKey,
            "ROLE_EMPLOYEE",
            socialType
        )
    }

    @Transactional
    fun getSocialUser(attributes: OAuth2Attributes, socialType: SocialType) : MemberResponseDto {
        return memberService.getSocialMember(
            attributes.oAuth2UserInfo.id ?: throw BaseException(ErrorCode.BAD_REQUEST), socialType)
            ?: createSocialUser(attributes, socialType)
    }

    @Transactional
    fun createSocialUser(attributes: OAuth2Attributes, socialType: SocialType) : MemberResponseDto {
        val oAuth2UserInfo: OAuth2UserInfo = attributes.oAuth2UserInfo
//        val name: StringBuilder = StringBuilder(oAuth2UserInfo.nickname?.replace(" ", "") ?:
//        throw BaseException(ErrorCode.BAD_REQUEST)
//        )
        val memberSignupDto = MemberSignupDto(
            id = oAuth2UserInfo.id!!,
            socialType = socialType,
            pwd = passwordEncoder().encode(UUID.randomUUID().toString().substring(0, 15)),
        )
        return memberService.signup(memberSignupDto)
    }

    private fun getSocialType(registrationId: String): SocialType {
        return when(registrationId){
            NAVER -> SocialType.NAVER
            KAKAO -> SocialType.KAKAO
            else -> SocialType.GOOGLE
        }
    }
    companion object{
        const val NAVER = "naver"
        const val KAKAO = "kakao"
    }
}
