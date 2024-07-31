package com.bubaum.pairing_server.cartitem.domain.repository

import com.bubaum.pairing_server.cartitem.domain.dto.CartItemResponseDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartSearchDto
import com.bubaum.pairing_server.cartitem.domain.entity.QCartItem.cartItem
import com.bubaum.pairing_server.file.domain.entity.QFile.file
import com.bubaum.pairing_server.product.domain.entity.QProduct.product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.QProductOptionGroup.productOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.entity.QProductOptionValue.productOptionValue
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository


@Repository
class CartItemRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CartItemRepositoryCustom {
    private fun getCartItemResponseDtoSelect(): ConstructorExpression<CartItemResponseDto> {
        return Projections.constructor(
            CartItemResponseDto::class.java,
            cartItem.idx,
            cartItem.count,
            product.idx,
            product.name,
            product.price,
            JPAExpressions.select(file.idx)
                .from(file)
                .where(file.isThumbnail.eq(true).and(file.product.idx.eq(product.idx))),
            product.price.add(productOptionGroup.addPrice).multiply(cartItem.count),
            cartItem.productOptionGroup.addPrice,
            JPAExpressions.select(
                Expressions.stringTemplate(
                "GROUP_CONCAT({0})",
                productOptionValue.value
                )
            ).from(productOptionValue)
                .where(productOptionValue.productOptionGroup.idx.eq(productOptionGroup.idx))
                .groupBy(productOptionValue.productOptionGroup.idx)
        )
    }
    override fun getCartItemPage(pageable: Pageable, userIdx : Long, cartSearchDto: CartSearchDto): Page<CartItemResponseDto> {

        val fetch = queryFactory.select(getCartItemResponseDtoSelect())
            .from(cartItem)
            .leftJoin(cartItem.user)
            .leftJoin(cartItem.productOptionGroup, productOptionGroup)
            .leftJoin(cartItem.product, product)
            .where(cartItem.user.idx.eq(userIdx))
            .orderBy(cartItem.idx.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(cartItem.idx.count())
            .from(cartItem)
            .leftJoin(cartItem.user)
            .where(cartItem.user.idx.eq(userIdx))
            .fetchFirst()

        return PageableExecutionUtils.getPage(fetch, pageable) {count}
    }

    override fun getCartItems(userIdx: Long, idxList: List<Long>) : List<CartItemResponseDto>? {
        return queryFactory.select(getCartItemResponseDtoSelect())
            .from(cartItem)
            .leftJoin(cartItem.user)
            .leftJoin(cartItem.productOptionGroup, productOptionGroup)
            .leftJoin(cartItem.product, product)
            .where(cartItem.user.idx.eq(userIdx), cartItem.idx.`in`(idxList))
            .orderBy(cartItem.idx.desc())
            .fetch()
    }


    override fun existsByCartItem(userIdx: Long, productOptionGroupIdxs: List<Long>): List<Long> {
        return queryFactory.select(cartItem.idx)
            .from(cartItem)
            .leftJoin(cartItem.user)
            .leftJoin(cartItem.productOptionGroup)
            .where(
                cartItem.user.idx.eq(userIdx),
                cartItem.productOptionGroup.idx.`in`(productOptionGroupIdxs)
            ).fetch()
    }

    //    override fun getOrderList(userIdx: Long): List<OrderListResponseDto> {
//        return queryFactory.select(
//            QOrderListResponseDto(
//                cartItem.idx,
//                order.address,
//                order.detailAddress,
//                order.id,
//                payment.orderId,
//                cartItem.count,
//                cartItem.product.idx,
//                cartItem.product.name,
//                cartItem.product.price,
//                JPAExpressions.select(file.idx)
//                    .from(file)
//                    .where(file.isThumbnail.eq(true).and(file.product.idx.eq(cartItem.product.idx))),
//                cartItem.productOptionChild.productOption.idx,
//                cartItem.productOptionChild.productOption.name,
//                cartItem.productOptionChild.idx,
//                cartItem.productOptionChild.name,
//                cartItem.productOptionChild.price,
//                cartItem.count.multiply(cartItem.product.price.add(cartItem.productOptionChild.price))
//            )
//        ).from(cartItem)
//            .innerJoin(cartItem.order, order)
//            .innerJoin(order.payment, payment)
//            .leftJoin(cartItem.user)
//            .leftJoin(cartItem.productOptionChild.productOption)
//            .leftJoin(cartItem.product)
//            .where(cartItem.user.idx.eq(userIdx))
//            .fetch()
//    }
}