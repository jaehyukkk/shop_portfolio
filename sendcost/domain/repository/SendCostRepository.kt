package com.bubaum.pairing_server.sendcost.domain.repository

import com.bubaum.pairing_server.sendcost.domain.entity.SendCost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SendCostRepository : JpaRepository<SendCost, Long> {
    fun findByOrderByIdxDesc(): List<SendCost>


    @Query("SELECT s FROM SendCost s WHERE CAST(s.startPostCode AS int) <= :postCode AND CAST(s.endPostCode AS int) >= :postCode")
    fun calculateShippingCost(postCode : Int) : SendCost?
}