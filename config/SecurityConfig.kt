package com.bubaum.pairing_server.config

import com.bubaum.pairing_server.exception.JwtExceptionHandler
import com.bubaum.pairing_server.handler.OAuth2LoginSuccessHandler
import com.bubaum.pairing_server.user.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtExceptionHandler: JwtExceptionHandler,
    private val oAuth2LoginSuccessHandler : OAuth2LoginSuccessHandler,
    private val customOAuth2UserService: CustomOAuth2UserService
    ){
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.csrf().disable()
        // Define public and private routes
        http.authorizeRequests()
            .antMatchers(
                "/**"
//                "/api/v1/user/login",
//                "/api/v1/user/test",
//                "/api/v1/user/refresh",
//                "/api/v1/user/signup",
//                "/v3/api-docs/**",
//                "/api-docs/**",
//                "/swagger*/**",
            ).permitAll()
            .anyRequest().authenticated() // In case you have a frontend

        // Other configuration
        http.cors().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.headers().frameOptions().disable()
        http.headers().xssProtection().disable()
        http.addFilterBefore(JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(jwtExceptionHandler, JwtAuthFilter(jwtTokenProvider)::class.java)
        http.oauth2Login().successHandler(oAuth2LoginSuccessHandler).userInfoEndpoint().userService(customOAuth2UserService)
        return http.build()
    }

    companion object{
        @Bean
        fun passwordEncoder(): PasswordEncoder {
            return BCryptPasswordEncoder()
        }
    }

}
