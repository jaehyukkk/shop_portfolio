package com.bubaum.pairing_server.banner.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Banner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val title: String,
    val content: String,
    val isEnabled: Boolean? = true,
    val orderNumber: Int
): Base() {
}