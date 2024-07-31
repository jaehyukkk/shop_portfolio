package com.bubaum.pairing_server.reviewoptionvalue.domain.entity

import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.ReviewOptionGroup
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ReviewOptionValue(

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,
    val name: String,
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    val reviewOptionGroup: ReviewOptionGroup
) {
}