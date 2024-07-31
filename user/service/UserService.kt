package com.bubaum.pairing_server.user.service

import com.bubaum.pairing_server.config.JwtTokenProvider
import com.bubaum.pairing_server.global.dto.TokenDto
import com.bubaum.pairing_server.user.domain.dto.LoginRequestDto
import com.bubaum.pairing_server.user.domain.dto.LoginResponseDto
import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.admin.domain.repository.AdminRepository
import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.global.service.RedisService
import com.bubaum.pairing_server.member.domain.repository.MemberRepository
import com.bubaum.pairing_server.user.domain.dto.ReissueDto
import com.bubaum.pairing_server.user.domain.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.transaction.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
    private val authenticationManagerBuilder : AuthenticationManagerBuilder,
    private val tokenProvider: JwtTokenProvider,
    private val adminRepository: AdminRepository,
    private val memberRepository: MemberRepository,
    private val redisService: RedisService

){

    @Transactional
    fun login(loginRequestDto: LoginRequestDto) : LoginResponseDto<Any> {
        try {
            val authenticationToken: UsernamePasswordAuthenticationToken = loginRequestDto.toAuthentication()
            val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
            val tokenResponseDto = tokenProvider.generateTokenDto(authentication)
            saveRefreshToken(authentication.name, tokenResponseDto)

            return LoginResponseDto(getUserInfo(authentication.name.toLong(), authentication.authorities), tokenResponseDto)
        } catch (e: BadCredentialsException) {
            throw BaseException(ErrorCode.UNAUTHORIZED)
        }

    }

    @Transactional
    fun socialLogin(user: User): TokenDto {
        val token =  tokenProvider.createSocialUserTokenDto(user)
        saveRefreshToken(user.idx.toString(), token)
        return token
    }

    fun getUserInfo(idx: Long, roles:Collection<GrantedAuthority>) : Any {
        val rolesString = roles.toString()
//        val admin = adminRepository.getUserInfo(idx)
//        println(admin?.idx)
//        println(idx)
        return if (rolesString.contains("ROLE_ADMIN")) {
            adminRepository.getUserInfo(idx)
        } else {
            memberRepository.getUserInfo(idx)
        } ?: throw BaseException(ErrorCode.NOT_FOUND_USER)
    }

    fun getOnlyMemberInfo(idx: Long) : MemberResponseDto {
        return memberRepository.getUserInfo(idx) ?: throw BaseException(ErrorCode.NOT_FOUND_USER)
    }

    fun getUser(idx: Long): User {
        return userRepository.findById(idx).orElseThrow()
    }
    @Transactional
    fun refresh(reissueDto: ReissueDto): TokenDto {
        if (!tokenProvider.validateToken(reissueDto.refreshToken)) {
            throw BaseException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        val authentication = tokenProvider.getAuthentication(reissueDto.accessToken)
//        val refreshToken = refreshTokenRepository.findByKey(authentication.name.toLong()) ?: throw BaseException(ErrorCode.LOGOUT_USER)
        val refreshToken = redisService.getValues("RTK:" + authentication.name) as String? ?: throw BaseException(ErrorCode.LOGOUT_USER)

        if (reissueDto.refreshToken != refreshToken) {
            throw BaseException(ErrorCode.NOT_MATCH_USER)
        }

        return tokenProvider.generateOnlyAccessToken(authentication)
    }

    fun selectSocialUser(socialType: SocialType, id : String) : User{
        val member = memberRepository.getSocialMember(id, socialType) ?: throw BaseException(ErrorCode.NOT_FOUND_USER)
        return userRepository.findById(member.idx).orElseThrow{
            throw BaseException(ErrorCode.NOT_FOUND_USER)
        }
    }

    private fun saveRefreshToken(userId: String, tokenDto: TokenDto) {
        redisService.setValues(
            "RTK:$userId",
            tokenDto.refreshToken!!,
            tokenDto.refreshTokenExpiresIn!!,
            TimeUnit.MILLISECONDS
        )
    }


}
