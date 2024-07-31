package com.bubaum.pairing_server.payment.domain.repository.payment.easypay

import com.bubaum.pairing_server.payment.domain.entity.EasyPay
import org.springframework.data.jpa.repository.JpaRepository

interface EasyPayRepository : JpaRepository<EasyPay, Long> {
}