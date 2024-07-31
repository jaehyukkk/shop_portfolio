package com.bubaum.pairing_server.user.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

//import com.querydsl.jpa.impl.JPAQueryFactory
//import javax.persistence.Entity

@Repository
class CustomUserRepositoryImpl (
    private val queryFactory: JPAQueryFactory


): CustomUserRepository {
    override fun test(): String {
        return "test"
    }

}