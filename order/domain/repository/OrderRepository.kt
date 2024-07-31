package com.bubaum.pairing_server.order.domain.repository

import com.bubaum.pairing_server.order.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>, OrderRepositoryCustom {
}

