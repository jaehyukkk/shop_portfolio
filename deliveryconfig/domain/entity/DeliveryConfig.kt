package com.bubaum.pairing_server.deliveryconfig.domain.entity

import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.DeliveryType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DeliveryConfig(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,
    val company: DeliveryCompany,
    val type: DeliveryType,
    val differentialPrices: String? = null
) {
}