package com.bubaum.pairing_server.user.controller

import com.bubaum.pairing_server.admin.domain.dto.AdminResponseDto
import com.bubaum.pairing_server.admin.domain.dto.AdminSignupDto
import com.bubaum.pairing_server.member.domain.dto.MemberSignupDto
import com.bubaum.pairing_server.user.domain.dto.LoginRequestDto
import com.bubaum.pairing_server.admin.service.AdminService
import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.global.dto.TokenDto
import com.bubaum.pairing_server.member.service.MemberService
import com.bubaum.pairing_server.user.domain.dto.LoginResponseDto
import com.bubaum.pairing_server.user.domain.dto.ReissueDto
import com.bubaum.pairing_server.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val adminService: AdminService,
    private val memberService: MemberService
    ) {

    //TODO: 권한 따라 나눠서 새로 만들어야함
//    @Operation(summary = "유저 정보 API")
//    @GetMapping("info")
//    fun getUserInfo(principal: Principal): ResponseEntity<ResultMsg> {
//
//        return ResponseEntity.ok(ResultMsg(result.OK.status, "유저 정보 조회 완료", userInfo))
//    }




    @Operation(summary = "로그인 API")
    @PostMapping("login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto<Any>> {
        return ResponseEntity.ok(userService.login(loginRequestDto))
    }


    @Operation(summary = "유저정보 API")
    @GetMapping("/member/info")
    fun getUserInfo(principal: Principal) : ResponseEntity<MemberResponseDto>{
        return ResponseEntity.ok(userService.getOnlyMemberInfo(principal.name.toLong()))
    }

    @Operation(summary = "토큰 재발급 API")
    @PostMapping("refresh")
    fun refresh(@RequestBody reissueDto: ReissueDto): ResponseEntity<TokenDto> {
        return ResponseEntity.ok(userService.refresh(reissueDto))
    }

    @Operation(summary = "관리자 회원가입 API")
    @PostMapping("/signup/admin")
    fun signup(@RequestBody adminSignupDto: AdminSignupDto) : ResponseEntity<AdminResponseDto> {
        return ResponseEntity.ok(adminService.signup(adminSignupDto))
    }

    @Operation(summary = "일반유저 회원가입 API")
    @PostMapping("/signup/member")
    fun signup(@RequestBody memberSignupDto: MemberSignupDto) : ResponseEntity<MemberResponseDto> {
        return ResponseEntity.ok(memberService.signup(memberSignupDto))
    }

}
