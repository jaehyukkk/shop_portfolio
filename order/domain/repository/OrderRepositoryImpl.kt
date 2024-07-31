package com.bubaum.pairing_server.order.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory

class OrderRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : OrderRepositoryCustom {

}