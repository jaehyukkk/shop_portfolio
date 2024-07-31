package com.bubaum.pairing_server.banner.service

import com.bubaum.pairing_server.banner.domain.dto.BannerRequestDto
import com.bubaum.pairing_server.banner.domain.entity.Banner
import com.bubaum.pairing_server.banner.domain.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class BannerService(
    private val bannerRepository: BannerRepository
) {
    fun create(bannerRequestDto: BannerRequestDto) : Banner{
        return bannerRepository.save(bannerRequestDto.toEntity())
    }
    fun getBannerList() : List<Banner>{
        return bannerRepository.findByIsEnabledOrderByOrderNumberDesc(isEnabled = true)
    }
}