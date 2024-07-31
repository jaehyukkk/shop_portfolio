package com.bubaum.pairing_server.membergrade.domain.repository

import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure

interface MemberGradeRepository : JpaRepository<MemberGrade, Long>{

    @Procedure("UpdateMemberGrade")
    fun updateMemberGradeProcedure()
}
