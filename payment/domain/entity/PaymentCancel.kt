package com.bubaum.pairing_server.payment.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import java.math.BigDecimal
import javax.persistence.*

@Entity
data class PaymentCancel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val cancelAmount: BigDecimal,
    val cancelReason: String,
    val taxFreeAmount: BigDecimal,
    val taxExemptionAmount: Int,
    val refundableAmount: BigDecimal,
    val easyPayDiscountAmount: BigDecimal,
    val canceledAt: String,
    val transactionKey: String,
    val receiptKey: String?,
    val orderId: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    var payment: Payment? = null
): Base() {
}