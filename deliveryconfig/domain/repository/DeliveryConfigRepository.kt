package com.bubaum.pairing_server.deliveryconfig.domain.repository

import com.bubaum.pairing_server.deliveryconfig.domain.entity.DeliveryConfig
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryConfigRepository : JpaRepository<DeliveryConfig, Long> {

    fun findFirstByIdxNotNull() : DeliveryConfig?
}