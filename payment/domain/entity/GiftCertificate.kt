package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "payment_gift_certificate")
data class GiftCertificate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("결제 승인번호")
    val approveNo: String,

    @Comment("정산 상태")
    val settlementStatus: String

)
