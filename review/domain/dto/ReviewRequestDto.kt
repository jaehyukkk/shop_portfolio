package com.bubaum.pairing_server.review.domain.dto

import com.bubaum.pairing_server.member.domain.entity.Member
import com.bubaum.pairing_server.orderitem.domain.entity.OrderItem
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.review.domain.entity.Review
import com.fasterxml.jackson.annotation.JsonIgnore

class ReviewRequestDto(
    val content: String,
    val rating: Int,
    val orderItemIdx: Long,
    val productIdx: Long,
    val reviewOptionValueIdxs: List<Long>,
) {
    @JsonIgnore
    lateinit var member: Member
    @JsonIgnore
    lateinit var orderItem: OrderItem

    @JsonIgnore
    lateinit var product: Product

    fun toEntity(): Review {
        return Review(
            content = content,
            rating = rating,
            member = member,
            orderItem = orderItem,
            product = product
        )
    }
}