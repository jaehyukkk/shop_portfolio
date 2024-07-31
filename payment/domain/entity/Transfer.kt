package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "transfers")
data class Transfer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("은행 숫자 코드")
    val bankCode: String,

    @Comment("정산 상태")
    val settlementStatus: String

    // 나머지 필드...
)