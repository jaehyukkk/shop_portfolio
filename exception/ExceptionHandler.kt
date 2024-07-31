package com.bubaum.pairing_server.exception

import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(CustomMessageRuntimeException::class)
    fun handleCustomMessageRuntimeException(e: CustomMessageRuntimeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode, e.msg)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }

//    @ExceptionHandler(CustomMessageRuntimeException::class)
//    protected fun handleBaseException(e: CustomMessageRuntimeException): ResponseEntity<ResultMsg> {
//        return ResponseEntity.status(e.result.status)
//            .body(ResultMsg(e.result.status, e.msg,null))
//    }
//
//
//    @ExceptionHandler(SecurityException::class)
//    fun handleSecurityJwtException(e:SecurityException) : ResponseEntity<ResultMsg> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMsg(result.FORBIDDEN.status, e.message.toString(),"잘못된 JWT 서명입니다."))
//    }
//
//    @ExceptionHandler(MalformedJwtException::class)
//    fun handleMalformedJwtException(e:MalformedJwtException) : ResponseEntity<ResultMsg> =
//        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMsg(result.FORBIDDEN.status, e.message.toString(),"올바르지 않은 토큰입니다."))
//
//    @ExceptionHandler(ExpiredJwtException::class)
//    fun handleExpiredJwtException(e:ExpiredJwtException) : ResponseEntity<ResultMsg> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMsg(result.FORBIDDEN.status, e.message.toString(),"토큰이 만료되었습니다. 다시 로그인해주세요."))
//    }
//    @ExceptionHandler(UnsupportedJwtException::class)
//    fun handleUnsupportedJwtException(e:UnsupportedJwtException) : ResponseEntity<ResultMsg> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMsg(result.FORBIDDEN.status, e.message.toString(),"지원되지 않는 JWT 토큰입니다."))
//    }
//    @ExceptionHandler(IllegalArgumentException::class)
//    fun handleSignatureException(e:IllegalArgumentException) : ResponseEntity<ResultMsg> =
//        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMsg(result.FORBIDDEN.status, e.message.toString(),"JWT 토큰이 잘못되었습니다."))





}

