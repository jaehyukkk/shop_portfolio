package com.bubaum.pairing_server.config

import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.user.domain.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailService (
    private val userRepository: UserRepository
) : UserDetailsService {


    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return createUserDetails(userRepository.findByUserId(username)?:
        throw UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다."))
    }

    private fun createUserDetails(user: User): UserDetails {
        val grantedAuthority: GrantedAuthority = SimpleGrantedAuthority(user.auths?:"")
        return org.springframework.security.core.userdetails.User(java.lang.String.valueOf(user.idx),
            user.pwd, setOf(grantedAuthority))
    }
}