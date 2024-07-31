package com.bubaum.pairing_server.admin.service

import com.bubaum.pairing_server.admin.domain.dto.AdminResponseDto
import com.bubaum.pairing_server.admin.domain.dto.AdminSignupDto
import com.bubaum.pairing_server.admin.domain.repository.AdminRepository
import com.bubaum.pairing_server.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(adminSignupDto: AdminSignupDto) : AdminResponseDto {
        adminSignupDto.user = userRepository.save(adminSignupDto.toUserEntity(passwordEncoder))
        return AdminResponseDto.of(adminRepository.save(adminSignupDto.toEntity()))
    }
}