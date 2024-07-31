package com.bubaum.pairing_server.payment.domain.entity

import org.hibernate.annotations.Comment
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "payment_card")
data class Card(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Comment("카드사에 결제 요청한 금액")
    val amount: BigDecimal,

    @Comment("카드 발급사 숫자 코드")
    val issuerCode: String,

    @Comment("카드 매입사 숫자 코드")
    val acquirerCode: String?,

    @Comment("카드번호 (마스킹된 부분 포함)")
    val number: String,

    @Comment("할부 개월 수")
    val installmentPlanMonths: Int,

    @Comment("카드사 승인 번호")
    val approveNo: String,

    @Comment("카드사 포인트 사용 여부")
    val useCardPoint: Boolean,

    @Comment("카드 종류 (신용, 체크, 기프트 등)")
    val cardType: String,

    @Comment("카드의 소유자 타입 (개인, 법인 등)")
    val ownerType: String,

    @Comment("카드 결제의 매입 상태")
    val acquireStatus: String,

    @Comment("무이자 할부의 적용 여부")
    val isInterestFree: Boolean,

    @Comment("할부가 적용된 결제에서 할부 수수료를 부담하는 주체 (고객, 카드사, 상점)")
    val interestPayer: String?
) {

}