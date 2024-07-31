package com.bubaum.pairing_server.reviewoptiontemplate.domain.repository

import com.bubaum.pairing_server.reviewoptiontemplate.domain.entity.ReviewOptionTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewOptionTemplateRepository : JpaRepository<ReviewOptionTemplate, Long>{
}