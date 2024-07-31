package com.bubaum.pairing_server.payment.domain.repository.payment.payment

import com.bubaum.pairing_server.order.domain.entity.QOrder.order
import com.bubaum.pairing_server.orderitem.domain.entity.QOrderItem.orderItem
import com.bubaum.pairing_server.payment.domain.dto.PaymentKeyResponseDto
import com.bubaum.pairing_server.payment.domain.dto.QPaymentKeyResponseDto
import com.bubaum.pairing_server.payment.domain.entity.QPayment.payment
import com.bubaum.pairing_server.user.domain.entity.QUser.user
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PaymentRepositoryCustom {

    override fun getPaymentKey(cartIdx: Long, userIdx: Long): PaymentKeyResponseDto? {
        return queryFactory.select(
            QPaymentKeyResponseDto(
                payment.paymentKey,
                orderItem.resultAmount
            )
        )
            .from(payment)
            .innerJoin(order).on(order.payment.id.eq(payment.id))
            .innerJoin(orderItem).on(orderItem.order.id.eq(order.id))
            .innerJoin(orderItem.user, user)
            .where(orderItem.idx.eq(cartIdx), user.idx.eq(userIdx))
            .fetchFirst()
    }
}