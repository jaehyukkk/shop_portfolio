package com.bubaum.pairing_server.admin.domain.repository

import com.bubaum.pairing_server.admin.domain.dto.AdminResponseDto
import com.bubaum.pairing_server.admin.domain.dto.QAdminResponseDto
import com.bubaum.pairing_server.admin.domain.entity.QAdmin.admin
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AdminRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : AdminRepositoryCustom {

    override fun getUserInfo(idx: Long): AdminResponseDto? {
        return queryFactory.select(
            QAdminResponseDto(
                admin.idx,
                admin.user.id,
                admin.user.auths,
                admin.phone
            )
        ).from(admin)
            .leftJoin(admin.user)
            .where(admin.idx.eq(idx))
            .fetchOne()
    }
}