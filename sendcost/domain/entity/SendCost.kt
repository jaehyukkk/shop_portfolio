package com.bubaum.pairing_server.sendcost.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class SendCost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val name: String,
    val price: Int,
    val startPostCode: String,
    val endPostCode: String,
    val isEnabled: Boolean? = true,
) {
}