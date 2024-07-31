package com.bubaum.pairing_server.payment.domain.dto.toss

import com.bubaum.pairing_server.payment.domain.entity.Payment
import com.bubaum.pairing_server.payment.domain.entity.PaymentCancel
import java.math.BigDecimal

data class TossPaymentsRequestDto(
    val version: String,
    val paymentKey: String,
    val type: String,
    val orderId: String,
    val orderName: String,
    val mId: String,
    val currency: String,
    val method: String,
    val totalAmount: BigDecimal,
    val balanceAmount: BigDecimal,
    val status: String,
    val requestedAt: String,
    val approvedAt: String,
    val useEscrow: Boolean,
    val lastTransactionKey: String?,
    val suppliedAmount: BigDecimal?,
    val vat: BigDecimal?,
    val cultureExpense: Boolean?,
    val taxFreeAmount: BigDecimal?,
    val taxExemptionAmount: Int?,
    val cancels: List<Cancel>?,
    val refundableAmount: BigDecimal?,
    val easyPayDiscountAmount: BigDecimal?,
    val canceledAt: String?,
    val transactionKey: String?,
    val receiptKey: String?,
    val isPartialCancelable: Boolean,
    val card: Card?,
    val virtualAccount: VirtualAccount?,
    val mobilePhone: MobilePhone?,
    val receiptUrl: String?,
    val giftCertificate: GiftCertificate?,
    val transfer: Transfer?,
    val receipt: Receipt?,
    val checkout: Checkout?,
    var easyPay: EasyPay?,
    val country: String?,
    val failure: Failure?,
    val cashReceipt: CashReceipt?,
    val cashReceipts: List<CashReceipt>?
){
    fun toEntity() : Payment {
        return Payment(
            type = type,
            orderId = orderId,
            orderName = orderName,
            mId = mId,
            currency = currency,
            method = method,
            totalAmount = totalAmount,
            balanceAmount = balanceAmount,
            status = status,
            requestedAt = requestedAt,
            approvedAt = approvedAt,
            useEscrow = useEscrow,
            paymentKey = paymentKey,
            suppliedAmount = suppliedAmount ?: BigDecimal.ZERO,
            vat = vat ?: BigDecimal.ZERO,

        )
    }
}

data class Cancel(
    val cancelAmount: BigDecimal,
    val cancelReason: String?,
    val taxFreeAmount: BigDecimal?,
    val taxExemptionAmount: Int?,
    val refundableAmount: BigDecimal,
    val easyPayDiscountAmount: BigDecimal,
    val canceledAt: String,
    val transactionKey: String,
    val receiptKey: String?
){
    fun toEntity(orderId: String) : PaymentCancel {
        return PaymentCancel(
            cancelAmount = cancelAmount,
            cancelReason = cancelReason ?: "",
            taxFreeAmount = taxFreeAmount ?: BigDecimal.ZERO,
            taxExemptionAmount = taxExemptionAmount ?: 0,
            refundableAmount = refundableAmount,
            easyPayDiscountAmount = easyPayDiscountAmount,
            canceledAt = canceledAt,
            transactionKey = transactionKey,
            receiptKey = receiptKey,
            orderId = orderId
        )
    }
}

data class Card(
    val amount: BigDecimal,
    val issuerCode: String,
    val acquirerCode: String?,
    val number: String,
    val installmentPlanMonths: Int,
    val approveNo: String,
    val useCardPoint: Boolean,
    val cardType: String,
    val ownerType: String,
    val acquireStatus: String,
    val isInterestFree: Boolean,
    val interestPayer: String?
){
    fun toEntity() : com.bubaum.pairing_server.payment.domain.entity.Card {
        return com.bubaum.pairing_server.payment.domain.entity.Card(
            amount = amount,
            issuerCode = issuerCode,
            acquirerCode = acquirerCode,
            number = number,
            installmentPlanMonths = installmentPlanMonths,
            approveNo = approveNo,
            useCardPoint = useCardPoint,
            cardType = cardType,
            ownerType = ownerType,
            acquireStatus = acquireStatus,
            isInterestFree = isInterestFree,
            interestPayer = interestPayer
        )
    }
}

data class VirtualAccount(
    val accountType: String,
    val accountNumber: String,
    val bankCode: String,
    val customerName: String,
    val dueDate: String,
    val refundStatus: String,
    val refundReceiveAccount: RefundReceiveAccount,
    val expired: Boolean,
    val settlementStatus: String
)

data class RefundReceiveAccount(
    val bankCode: String,
    val accountNumber: String,
    val holderName: String
)

data class MobilePhone(
    val customerMobilePhone: String,
    val settlementStatus: String,
    val receiptUrl: String
)

data class GiftCertificate(
    val approveNo: String,
    val settlementStatus: String
)

data class Transfer(
    val bankCode: String,
    val settlementStatus: String
)

data class Receipt(
    val url: String
)

data class Checkout(
    val url: String
)

data class EasyPay(
    val provider: String,
    val amount: BigDecimal,
    val discountAmount: BigDecimal
){
    fun toEntity() : com.bubaum.pairing_server.payment.domain.entity.EasyPay {
        return com.bubaum.pairing_server.payment.domain.entity.EasyPay(
            provider = provider,
            amount = amount,
            discountAmount = discountAmount
        )
    }
}

data class Failure(
    val code: String,
    val message: String
)

data class CashReceipt(
    val type: String,
    val receiptKey: String,
    val issueNumber: String,
    val receiptUrl: String,
    val amount: Int,
    val taxFreeAmount: Int,
    val issueStatus: String,
    val failure: Failure?,
    val customerIdentityNumber: String,
    val requestedAt: String
)