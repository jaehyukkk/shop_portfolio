package com.bubaum.pairing_server.aop

import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.utils.enum.result
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@Aspect
class AdminAspect {
    @Around("@annotation(AdminRights)")
    @Throws(Throwable::class)
    fun userRights(joinPoint: ProceedingJoinPoint): Any? {
        val authentication = SecurityContextHolder.getContext().authentication

        val hasUserRole = authentication.authorities.stream()
            .anyMatch { r: GrantedAuthority? -> r!!.authority == "ROLE_ADMIN" }

        if (!hasUserRole) {
            throw BaseException(ErrorCode.FORBIDDEN)

        }
        return joinPoint.proceed()
    }
}
