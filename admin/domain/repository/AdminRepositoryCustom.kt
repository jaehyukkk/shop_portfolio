package com.bubaum.pairing_server.admin.domain.repository

import com.bubaum.pairing_server.admin.domain.dto.AdminResponseDto

interface AdminRepositoryCustom {

    fun getUserInfo(idx : Long) : AdminResponseDto?
}