package com.bubaum.pairing_server.review.domain.repository

import com.bubaum.pairing_server.review.domain.dto.ReviewPercentageResponseDto
import com.bubaum.pairing_server.review.domain.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Query("""
            SELECT
                rating,
                COUNT(*) * 100.0 / SUM(COUNT(*)) OVER () AS percentage
            FROM
                review
            WHERE product_idx = :productIdx
            GROUP BY
                rating
            ORDER BY
                rating DESC
    """, nativeQuery = true)
    fun getRatingPercentage(productIdx: Long) : List<ReviewPercentageResponseDto>
}