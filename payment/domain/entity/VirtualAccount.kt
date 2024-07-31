package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "payment_virtual_account")
data class VirtualAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("가상계좌 타입 (일반, 고정 중 하나)")
    val accountType: String,

    @Comment("발급된 계좌번호")
    val accountNumber: String,

    @Comment("가상계좌 은행 숫자 코드")
    val bankCode: String,

    @Comment("가상계좌를 발급한 고객 이름")
    val customerName: String,

    @Comment("입금 기한")
    val dueDate: String,

    @Comment("환불 처리 상태")
    val refundStatus: String,

    @Comment("가상계좌가 만료되었는지 여부")
    val expired: Boolean,

    @Comment("정산 상태 (미완료, 완료)")
    val settlementStatus: String

)