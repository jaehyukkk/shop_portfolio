package com.bubaum.pairing_server.productoptiongroup.domain.dto

import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.dto.ProductOptionValueRequestDto

class ProductOptionGroupRequestDto(
    val idx: Long? = null,
    val addPrice: Int? = 0,
    val stock: Int? = 0,
    val productOptionValues: List<ProductOptionValueRequestDto>? = null
) {

    fun toEntity(product: Product): ProductOptionGroup {
        return ProductOptionGroup(
            idx = idx,
            addPrice = addPrice,
            stock = stock,
            product = product
        )
    }
}