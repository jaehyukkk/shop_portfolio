package com.bubaum.pairing_server.config

import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.global.dto.TokenDto
import com.bubaum.pairing_server.user.domain.entity.User
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class JwtTokenProvider(@Value("\${jwt.secret}") secretKey: String) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_TYPE = "bearer"
        //        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 120
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 3
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7
//        private const val ACCESS_TOKEN_EXPIRE_TIME = 15000
//        private const val REFRESH_TOKEN_EXPIRE_TIME = 30000 * 2
    }

    private val key: Key

    init {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }



    fun generateTokenDto(authentication: Authentication): TokenDto {
        // 권한들 가져오기
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        return getTokenDto(authentication.name, authorities)
    }

    fun generateOnlyAccessToken(authentication: Authentication) : TokenDto {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        val now = Date().time

        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenDto(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time,
        )
    }


    fun createSocialUserTokenDto(user: User) : TokenDto {
        val authorities = user.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        return getTokenDto(user.idx.toString(), authorities)
    }

//    fun createTempTokenDto(user : com.bubaum.pairing_server.user.domain.entity.User) : String{
//        val now = Date().time
//        val accessTokenExpiresIn = Date(now + 500 * 10)
//        return Jwts.builder()
//            .setSubject(user.idx.toString())
//            .claim(AUTHORITIES_KEY, "GUEST")
//            .setExpiration(accessTokenExpiresIn)
//            .signWith(key, SignatureAlgorithm.HS512)
//            .compact()
//    }

    private fun getTokenDto(idx : String, authorities : String): TokenDto {
        val now = Date().time

        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val refreshTokenExpiresIn = Date(now + REFRESH_TOKEN_EXPIRE_TIME)

        val accessToken = Jwts.builder()
            .setSubject(idx)
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setExpiration(refreshTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenDto(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time,
            refreshTokenExpiresIn = refreshTokenExpiresIn.time
        )
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)

        if (claims[AUTHORITIES_KEY] == null) {
            throw BaseException(ErrorCode.UNAUTHORIZED)
        }

        val authorities = claims[AUTHORITIES_KEY]
            .toString()
            .split(",")
            .map(::SimpleGrantedAuthority)
            .toList()


        return UsernamePasswordAuthenticationToken(claims.subject, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: io.jsonwebtoken.security.SecurityException) {
            log.info("잘못된 JWT 서명입니다.")
            throw JwtException("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.")
            throw JwtException("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
            throw JwtException("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
            throw JwtException("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.")
            throw JwtException("JWT 토큰이 잘못되었습니다.")
        }
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

}
