package com.bubaum.pairing_server.reviewoptiontemplate.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ReviewOptionTemplate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val name: String,
    val content: String,
) : Base() {
}