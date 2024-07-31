package com.bubaum.pairing_server.cartitem.domain.dto

import com.bubaum.pairing_server.cartitem.domain.entity.CartItem
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema

class CartItemRequestDto(
    @Schema(description = "상품 idx", defaultValue = "13")
    val productIdx: Long,
    val cartItems : List<ChildCartItemRequestDto>
) {
    companion object {
        class ChildCartItemRequestDto(
            @Schema(description = "상품갯수", defaultValue = "2")
            val count: Int,

            @Schema(description = "옵션 그룹 idx", defaultValue = "1")
            val productOptionGroupIdx: Long
        ) {
            @JsonIgnore
            lateinit var product: Product

            @JsonIgnore
            lateinit var user: User

            @JsonIgnore
            lateinit var productOptionGroup: ProductOptionGroup

            fun toEntity(): CartItem {
                return CartItem(
                    count = count,
                    product = product,
                    user = user,
                    productOptionGroup = productOptionGroup
                )
            }

        }
    }
}