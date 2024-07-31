package com.bubaum.pairing_server.member.domain.repository

import com.bubaum.pairing_server.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom {
}