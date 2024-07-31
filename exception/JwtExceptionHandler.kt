package com.bubaum.pairing_server.exception

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.io.IOException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtExceptionHandler(
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {
            filterChain.doFilter(request, response) // JwtAuthenticationFilter로 이동
        } catch (ex: JwtException) {
//            resolver.resolveException(request,response,null,ex)
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex)
        }
    }

    @Throws(IOException::class)
    fun setErrorResponse(status: HttpStatus, res: HttpServletResponse, ex: Throwable) {
        val mapper = ObjectMapper().registerKotlinModule()
        res.status = status.value()
        res.contentType = "application/json; charset=UTF-8"
        val jwtExceptionResponse = ResultMsg(result.FORBIDDEN.status, ex.message ?: "비정상적인 토큰입니다.")
        res.writer.write(mapper.writeValueAsString(jwtExceptionResponse))
    }


}


