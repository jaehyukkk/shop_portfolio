package com.bubaum.pairing_server.config

import com.bubaum.pairing_server.exception.JwtExceptionHandler
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class JwtSecurityConfig(
    private val tokenProvider: JwtTokenProvider,
    private val jwtExceptionHandler: JwtExceptionHandler,
    ) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        val customFilter = JwtAuthFilter(tokenProvider)
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(jwtExceptionHandler, JwtAuthFilter::class.java)
    }
}

