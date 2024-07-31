package com.bubaum.pairing_server.admin.domain.repository

import com.bubaum.pairing_server.admin.domain.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long>, AdminRepositoryCustom {
}