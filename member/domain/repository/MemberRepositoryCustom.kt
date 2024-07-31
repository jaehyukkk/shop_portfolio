package com.bubaum.pairing_server.member.domain.repository

import com.bubaum.pairing_server.member.domain.dto.MemberResponseDto
import com.bubaum.pairing_server.enums.SocialType

interface MemberRepositoryCustom {

    fun getUserInfo(idx : Long) : MemberResponseDto?

    fun getSocialMember(id: String, socialType: SocialType) : MemberResponseDto?
}
