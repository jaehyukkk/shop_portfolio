package com.bubaum.pairing_server.admin.domain.dto

import com.bubaum.pairing_server.admin.domain.entity.Admin
import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.password.PasswordEncoder

class AdminSignupDto(
    val id: String,
    private val pwd: String,
    val phone: String
) {

    @JsonIgnore
    lateinit var user : User

    fun toUserEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            id = id,
            pwd = passwordEncoder.encode(pwd),
            auths = "ROLE_ADMIN",
        )
    }

    fun toEntity() : Admin {
        return Admin(
            idx = user.idx,
            phone = phone,
            user = user
        )
    }
}
