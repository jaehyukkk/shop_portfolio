package com.bubaum.pairing_server.reviewoptionvaluereview.domain.repository

import com.bubaum.pairing_server.reviewoptionvaluereview.domain.entity.ReviewOptionValueReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewOptionValueReviewRepository : JpaRepository<ReviewOptionValueReview, Long>{
}