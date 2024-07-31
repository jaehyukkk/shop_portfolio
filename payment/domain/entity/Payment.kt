package com.bubaum.pairing_server.payment.domain.entity
import com.bubaum.pairing_server.global.entity.Base
import org.hibernate.annotations.Comment
import java.math.BigDecimal
import javax.persistence.*
@Entity
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("결제의 고유한 키 값")
    val paymentKey: String,

    @Comment("결제 타입 (일반결제, 자동결제, 브랜드페이 중 하나)")
    val type: String,

    @Comment("주문 ID, 가맹점에서 생성한 고유 주문 식별자")
    val orderId: String,

    @Comment("주문명")
    val orderName: String,

    @Comment("상점 아이디 (MID)")
    val mId: String,

    @Comment("결제에 사용한 통화")
    val currency: String,

    @Comment("결제 수단 (카드, 가상계좌, 간편결제, 휴대폰 등)")
    val method: String,

    @Comment("총 결제 금액")
    val totalAmount: BigDecimal,

    @Comment("취소 가능한 금액 (잔고)")
    val balanceAmount: BigDecimal,

    @Comment("공급가액")
    val suppliedAmount : BigDecimal,

    @Comment("부가세")
    val vat : BigDecimal,

    @Comment("결제 처리 상태")
    val status: String,

    @Comment("결제가 일어난 날짜와 시간 정보")
    val requestedAt: String,

    @Comment("결제 승인이 일어난 날짜와 시간 정보")
    val approvedAt: String,

    @Comment("에스크로 사용 여부")
    val useEscrow: Boolean,

    ): Base(){
    @OneToOne(fetch = FetchType.LAZY)
    var card: Card? = null

    @OneToOne(fetch = FetchType.LAZY)
    var virtualAccount: VirtualAccount? = null

    @OneToOne(fetch = FetchType.LAZY)
    var mobilePhone: MobilePhone? = null

    @OneToOne(fetch = FetchType.LAZY)
    var giftCertificate: GiftCertificate? = null

    @OneToOne(fetch = FetchType.LAZY)
    var transfer: Transfer? = null

    @OneToOne(fetch = FetchType.LAZY)
    var easyPay: EasyPay? = null

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    var paymentCancels: MutableSet<PaymentCancel> = LinkedHashSet()
    fun addCancels(paymentCancel: PaymentCancel) {
        paymentCancels.add(paymentCancel)
        paymentCancel.payment = this
    }
}