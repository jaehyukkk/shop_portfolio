package com.bubaum.pairing_server.reviewoptionvaluereview.domain.entity

import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.ReviewOptionValue
import com.bubaum.pairing_server.review.domain.entity.Review
import javax.persistence.*

@Entity
class ReviewOptionValueReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_option_value_id")
    val reviewOptionValue: ReviewOptionValue,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    val review: Review,
) {
}