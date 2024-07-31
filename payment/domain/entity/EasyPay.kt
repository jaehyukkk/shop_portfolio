package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@javax.persistence.Table(name = "payment_easy_pay")
data class EasyPay(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("선택한 간편결제사 코드")
    val provider: String,

    @Comment("간편결제 서비스에 등록된 계좌 혹은 현금성 포인트로 결제한 금액")
    val amount: BigDecimal,

    @Comment("간편결제 서비스의 적립 포인트나 쿠폰 등으로 즉시 할인된 금액")
    val discountAmount : BigDecimal? = null,
) {
}