//package com.bubaum.pairing_server.utils.global
//
//import com.bubaum.pairing_server.admin.domain.dto.AdminResponseDto
//import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
//import com.bubaum.pairing_server.utils.enum.result
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import java.security.Principal
//
//object UserInfoUtil {
//
//    fun principalToUserIdx(principal: Principal): AdminResponseDto {
//        if (principal is UsernamePasswordAuthenticationToken) {
//            userInfo = principal.principal as AdminResponseDto
//        } else {
//            throw CustomMessageRuntimeException(result.UNAUTHORIZED, "권한이 없습니다")
//        }
//    }
//}