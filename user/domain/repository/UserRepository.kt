package com.bubaum.pairing_server.user.domain.repository

import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.enums.SocialType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Long> , CustomUserRepository {
//    fun findById(id: String): User?

    fun findByIdAndSocialType(id: String, socialType: SocialType): User?

    @Query("select u from User u where u.id = :id")
    fun findByUserId(id: String): User?
}