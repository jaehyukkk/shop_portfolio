package com.bubaum.pairing_server.member.service

import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.member.domain.dto.MemberSignupDto
import com.bubaum.pairing_server.enums.SocialType
import com.bubaum.pairing_server.member.domain.entity.Member
import com.bubaum.pairing_server.member.domain.repository.MemberRepository
import com.bubaum.pairing_server.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signup(memberSignupDto: MemberSignupDto) : MemberResponseDto {
        memberSignupDto.user = userRepository.save(memberSignupDto.toUserEntity(passwordEncoder))
        return MemberResponseDto.of(memberRepository.save(memberSignupDto.toEntity()))
    }

    fun getSocialMember(id: String, socialType: SocialType) : MemberResponseDto? {
        return memberRepository.getSocialMember(id, socialType)
    }
    fun getMember(idx: Long) : Member {
        return memberRepository.findById(idx).orElseThrow()
    }
}
