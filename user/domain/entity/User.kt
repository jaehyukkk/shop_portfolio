package com.bubaum.pairing_server.user.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.enums.SocialType
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("유저 pk")
    val idx: Long? = null,
    @Comment("유저 아이디")
    @Column(length = 100 , nullable = false)
    val id:String,
    @Comment("유저 비밀번호")
    @Column(length = 200, nullable = false)
    var pwd:String,
    val auths:String,
    val deleted: Boolean = false,

    var socialType: SocialType? = null,

    ): Base() ,UserDetails{

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val roles: MutableSet<GrantedAuthority> = HashSet()
        for (role in auths.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            roles.add(SimpleGrantedAuthority(role))
        }
        return roles
    }

    override fun getPassword(): String {
        return pwd;
    }

    override fun getUsername(): String {
        return id;
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
    fun lookinfo():Unit{
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(this)
        System.out.println(userJson)

    }
}

