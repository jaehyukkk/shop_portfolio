package com.bubaum.pairing_server.reviewoptionvalue.domain.repository

import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.ReviewOptionValue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewOptionsValueRepository : JpaRepository<ReviewOptionValue, Long> {
}