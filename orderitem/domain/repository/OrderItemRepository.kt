package com.bubaum.pairing_server.orderitem.domain.repository

import com.bubaum.pairing_server.orderitem.domain.entity.OrderItem
import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface OrderItemRepository : JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {

    @Transactional
    @Modifying
    @Query("update OrderItem a set a.status = :status where a.idx = :idx")
    fun modifyStatus(
        idx: Long,
        status: OrderStatus
    ) : Int

    @Transactional
    @Modifying
    @Query("update OrderItem a set a.deliveryCompany = :deliveryCompany, a.invoiceNumber = :invoiceNumber, a.status = :status where a.idx = :idx")
    fun modifyDispatchInfo(
        deliveryCompany: DeliveryCompany,
        invoiceNumber: Long,
        status: OrderStatus,
        idx: Long
    ) : Int

    fun findByIdxAndUserIdxAndStatus(idx: Long, userIdx: Long, status: OrderStatus) : OrderItem?
}