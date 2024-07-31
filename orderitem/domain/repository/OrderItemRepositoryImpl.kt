package com.bubaum.pairing_server.orderitem.domain.repository

import com.bubaum.pairing_server.file.domain.entity.QFile.file
import com.bubaum.pairing_server.orderitem.domain.dto.*
import com.bubaum.pairing_server.orderitem.domain.entity.QOrderItem.orderItem
import com.bubaum.pairing_server.productoptionvalue.domain.entity.QProductOptionValue.productOptionValue
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class OrderItemRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : OrderItemRepositoryCustom {
//    override fun getOrderItemList(userIdx: Long): List<OrderListResponseDto> {
//        return queryFactory.select(
//            QOrderListResponseDto(
//                orderItem.idx,
//                order.address,
//                order.detailAddress,
//                order.id,
//                payment.orderId,
//                orderItem.count,
//                orderItem.product.idx,
//                orderItem.product.name,
//                orderItem.product.price,
//                JPAExpressions.select(file.idx)
//                    .from(file)
//                    .where(file.isThumbnail.eq(true).and(file.product.idx.eq(orderItem.product.idx))),
//                orderItem.productOptionChild.productOption.idx,
//                orderItem.productOptionChild.productOption.name,
//                orderItem.productOptionChild.idx,
//                orderItem.productOptionChild.name,
//                orderItem.productOptionChild.price,
//                orderItem.count.multiply(orderItem.product.price.add(orderItem.productOptionChild.price))
//            )
//        ).from(orderItem)
//            .innerJoin(orderItem.order, order)
//            .innerJoin(order.payment, payment)
//            .leftJoin(orderItem.user)
//            .leftJoin(orderItem.productOptionChild.productOption)
//            .leftJoin(orderItem.product)
//            .where(orderItem.user.idx.eq(userIdx))
//            .fetch()
//    }


//    val orderItemIdx : Long,
//    val orderIdx: Long,
//    val status: OrderStatus,
//    val orderId : String,
//    val productName : String,
//    val productOptions: String,
//    val count: Int,
//    val price: Int,
//    val couponName: String
    override fun getDashboardOrderItems(
        pageable: Pageable,
        dashboardOrderItemSearchDto: DashboardOrderItemSearchDto
    ): Page<DashboardOrderItemListResponseDto> {
        val query = queryFactory.select(
            QDashboardOrderItemListResponseDto(
                orderItem.idx,
                orderItem.order.id,
                orderItem.status,
                orderItem.order.payment.orderId,
                orderItem.product.name,
                JPAExpressions.select(
                    Expressions.stringTemplate(
                        "GROUP_CONCAT({0})",
                        productOptionValue.value
                    )
                ).from(productOptionValue)
                    .where(productOptionValue.productOptionGroup.idx.eq(orderItem.productOptionGroup.idx))
                    .groupBy(productOptionValue.productOptionGroup.idx),
                orderItem.count,
                orderItem.resultAmount,
                orderItem.coupon.coupon.name,
                orderItem.createDate
            )
        ).from(orderItem)
            .leftJoin(orderItem.order)
            .leftJoin(orderItem.order.payment)
            .leftJoin(orderItem.product)
            .leftJoin(orderItem.coupon.coupon)
            .leftJoin(orderItem.productOptionGroup)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()


    val count = queryFactory.select(orderItem.idx.count())
        .from(orderItem)
        .fetchFirst()

    return PageableExecutionUtils.getPage(query, pageable) {count}

    }

    override fun getUserOrderItems(
        pageable: Pageable,
        userIdx: Long
    ): Page<UserOrderItemListResponseDto> {
        val query = queryFactory.select(
            QUserOrderItemListResponseDto(
                orderItem.idx,
                orderItem.order.id,
                orderItem.status,
                JPAExpressions.select(file.idx)
                    .from(file)
                    .where(file.isThumbnail.eq(true).and(file.product.idx.eq(orderItem.product.idx))),
                orderItem.product.name,
                JPAExpressions.select(
                    Expressions.stringTemplate(
                        "GROUP_CONCAT({0})",
                        productOptionValue.value
                    )
                ).from(productOptionValue)
                    .where(productOptionValue.productOptionGroup.idx.eq(orderItem.productOptionGroup.idx))
                    .groupBy(productOptionValue.productOptionGroup.idx),
                orderItem.count,
                orderItem.resultAmount,
                orderItem.coupon.coupon.name,
                orderItem.product.idx,
                orderItem.createDate
            )
        ).from(orderItem)
            .leftJoin(orderItem.order)
            .leftJoin(orderItem.order.payment)
            .leftJoin(orderItem.product)
            .leftJoin(orderItem.coupon.coupon)
            .leftJoin(orderItem.productOptionGroup)
            .where(orderItem.user.idx.eq(userIdx))
            .orderBy(orderItem.createDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(orderItem.idx.count())
            .from(orderItem)
            .where(orderItem.user.idx.eq(userIdx))
            .fetchFirst()

        return PageableExecutionUtils.getPage(query, pageable) {count}
    }

    override fun getDashboardDeliveryManagementList(
        pageable: Pageable,
        dashboardDeliveryManagementSearchDto: DashboardDeliveryManagementSearchDto
    ): Page<DashboardDeliveryManagementListDto> {
    val query = queryFactory.select(
        QDashboardDeliveryManagementListDto(
            orderItem.idx,
            orderItem.order.id,
            orderItem.status,
            orderItem.order.payment.orderId,
            orderItem.product.name,
            JPAExpressions.select(
                Expressions.stringTemplate(
                    "GROUP_CONCAT({0})",
                    productOptionValue.value
                )
            ).from(productOptionValue)
                .where(productOptionValue.productOptionGroup.idx.eq(orderItem.productOptionGroup.idx))
                .groupBy(productOptionValue.productOptionGroup.idx),
            orderItem.count,
            orderItem.resultAmount,
            orderItem.deliveryCompany,
            orderItem.invoiceNumber,
            orderItem.order.deliveryInfo.name,
            orderItem.order.deliveryInfo.addr,
            orderItem.order.deliveryInfo.detailAddr,
            orderItem.order.deliveryInfo.phone,
            orderItem.createDate
        )
    ).from(orderItem)
        .leftJoin(orderItem.order.deliveryInfo)
        .leftJoin(orderItem.order.payment)
        .leftJoin(orderItem.product)
        .leftJoin(orderItem.coupon.coupon)
        .leftJoin(orderItem.productOptionGroup)
        .orderBy(orderItem.idx.desc())
        .offset(pageable.offset)
        .limit(pageable.pageSize.toLong())
        .fetch()


    val count = queryFactory.select(orderItem.idx.count())
        .from(orderItem)
        .fetchFirst()

    return PageableExecutionUtils.getPage(query, pageable) {count}
    }
}