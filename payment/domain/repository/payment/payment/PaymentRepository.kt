package com.bubaum.pairing_server.payment.domain.repository.payment.payment

import com.bubaum.pairing_server.payment.domain.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Repository
interface PaymentRepository : JpaRepository<Payment, Long>, PaymentRepositoryCustom {

    fun findByOrderId(orderId: String): Payment?

    @Transactional
    @Modifying
    @Query("update Payment a set a.totalAmount = :totalAmount, a.balanceAmount = :balanceAmount, a.status = :status, a.suppliedAmount = :suppliedAmount, a.vat = :vat  where a.id = :id")
    fun setPaymentCancelData(
        id: Long,
        totalAmount: BigDecimal,
        balanceAmount: BigDecimal,
        status: String,
        suppliedAmount: BigDecimal,
        vat: BigDecimal
    ) : Int
}