package com.bubaum.pairing_server.payment.domain.repository.paymentcancel

import com.bubaum.pairing_server.payment.domain.entity.PaymentCancel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentCancelRepository : JpaRepository<PaymentCancel, Long>{
}