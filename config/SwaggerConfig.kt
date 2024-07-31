package com.bubaum.pairing_server.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
@EnableWebMvc
class SwaggerConfig {
    private val jwtSchemeName = "JWT AUTH"
    private val securityRequirement = SecurityRequirement().addList(jwtSchemeName)
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(components)
        .addSecurityItem(securityRequirement)
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("피엔에스 홈페이지 API")
        .description("피엔에스 홈페이지 API 테스트")
        .version("1.0.0")


    var accessTokenSecurityScheme: SecurityScheme = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .`in`(SecurityScheme.In.HEADER)
        .name(jwtSchemeName)


    var components: Components = Components()
        .addSecuritySchemes(jwtSchemeName, accessTokenSecurityScheme)

}