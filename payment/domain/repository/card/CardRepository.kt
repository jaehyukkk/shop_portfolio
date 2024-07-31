package com.bubaum.pairing_server.payment.domain.repository.card

import com.bubaum.pairing_server.payment.domain.entity.Card
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<Card, Long> {
}