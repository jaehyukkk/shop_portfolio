package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "payment_mobile_phone")
data class MobilePhone(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("결제에 사용한 휴대폰 번호")
    val customerMobilePhone: String,

    @Comment("정산 상태")
    val settlementStatus: String,

    @Comment("휴대폰 결제 내역 영수증 확인 주소")
    val receiptUrl: String

)