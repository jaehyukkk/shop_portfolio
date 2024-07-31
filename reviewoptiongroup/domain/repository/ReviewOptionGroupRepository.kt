package com.bubaum.pairing_server.reviewoptiongroup.domain.repository

import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.ReviewOptionGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewOptionGroupRepository : JpaRepository<ReviewOptionGroup, Long>, ReviewOptionGroupRepositoryCustom {
}