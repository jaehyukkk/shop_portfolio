package com.bubaum.pairing_server.banner.domain.repository

import com.bubaum.pairing_server.banner.domain.entity.Banner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository : JpaRepository<Banner, Long>{
    fun findByIsEnabledOrderByOrderNumberDesc(isEnabled: Boolean) : List<Banner>
}