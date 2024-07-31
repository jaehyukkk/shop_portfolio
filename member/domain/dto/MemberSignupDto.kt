package com.bubaum.pairing_server.member.domain.dto

import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.member.domain.entity.Member
import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.password.PasswordEncoder

class MemberSignupDto(
    val id: String,
    private val pwd: String,
    val phone: String? = null,
    val socialType: SocialType? = null
) {

    @JsonIgnore
    lateinit var user : User

    fun toUserEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            id = id,
            pwd = passwordEncoder.encode(pwd),
            auths = "ROLE_EMPLOYEE",
        )
    }

    fun toEntity() : Member {
        return Member(
            idx = user.idx,
            user = user,
            socialType = socialType
        )
    }

}
