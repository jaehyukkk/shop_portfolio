package com.bubaum.pairing_server.member.domain.repository

import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.member.domain.dto.QMemberResponseDto
import com.bubaum.pairing_server.member.domain.entity.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun getUserInfo(idx: Long): MemberResponseDto? {
        return queryFactory.select(
            QMemberResponseDto(
                member.idx,
                member.user.id,
                member.user.auths,
            )
        ).from(member)
            .leftJoin(member.user)
            .where(member.user.idx.eq(idx))
            .fetchOne()
    }

    override fun getSocialMember(id: String, socialType: SocialType): MemberResponseDto? {
        return queryFactory.select(
            QMemberResponseDto(
                member.idx,
                member.user.id,
                member.user.auths,
            )
        ).from(member)
            .leftJoin(member.user)
            .where(member.user.id.eq(id).and(member.socialType.eq(socialType)))
            .fetchOne()
    }
}
