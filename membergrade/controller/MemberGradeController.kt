package com.bubaum.pairing_server.membergrade.controller

import com.bubaum.pairing_server.membergrade.domain.dto.MemberGradeRequestDto
import com.bubaum.pairing_server.membergrade.domain.dto.MemberGradeResponseDto
import com.bubaum.pairing_server.membergrade.domain.entity.MemberGrade
import com.bubaum.pairing_server.membergrade.service.MemberGradeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/member-grade")
@RestController
class MemberGradeController(
    private val memberGradeService: MemberGradeService
) {

    @PostMapping()
    fun create(@RequestBody memberRankRequestDto: MemberGradeRequestDto): ResponseEntity<MemberGrade> {
        return ResponseEntity.ok(memberGradeService.create(memberRankRequestDto))
    }

    @GetMapping()
    fun get() : ResponseEntity<List<MemberGradeResponseDto>> {
        return ResponseEntity.ok(memberGradeService.list())
    }
}
