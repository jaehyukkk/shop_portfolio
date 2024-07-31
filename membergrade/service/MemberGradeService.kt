package com.bubaum.pairing_server.membergrade.service

import com.bubaum.pairing_server.membergrade.domain.dto.MemberGradeRequestDto
import com.bubaum.pairing_server.membergrade.domain.dto.MemberGradeResponseDto
import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade
import com.bubaum.pairing_server.membergrade.domain.repository.MemberGradeRepository
import org.springframework.stereotype.Service

@Service
class MemberGradeService(
    private val memberGradeRepository: MemberGradeRepository
) {

    fun create(memberRankRequestDto: MemberGradeRequestDto) : MemberGrade {
        return memberGradeRepository.save(memberRankRequestDto.toEntity())
    }

    fun list() : List<MemberGradeResponseDto> {
        return memberGradeRepository.findAll().map { MemberGradeResponseDto.of(it) }
    }

    fun updateMemberGradeProcedure() {
        memberGradeRepository.updateMemberGradeProcedure()
    }
}
