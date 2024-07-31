package com.bubaum.pairing_server.deliveryinfo.domain.repository

import com.bubaum.pairing_server.deliveryinfo.domain.entity.DeliveryInfo
import com.bubaum.pairing_server.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface DeliveryInfoRepository : JpaRepository<DeliveryInfo, Long>{

    fun findByUser(user: User) : List<DeliveryInfo>

    @Modifying
    @Transactional
    @Query("update DeliveryInfo d set d.isDefault = true where d.user = :user and d.idx = :deliveryInfoIdx")
    fun modifyIsDefault(deliveryInfoIdx: Long, user: User) : Int

    @Modifying
    @Transactional
    @Query("update DeliveryInfo d set d.isDefault = false where d.user = :user and d.idx != :deliveryInfoIdx")
    fun modifyIsDefaultFalse(deliveryInfoIdx: Long, user: User) : Int

    @Modifying
    @Transactional
    @Query("update DeliveryInfo d set d.isDeleted = false")
    fun modifyAllIsDeletedFalse() : Int
}