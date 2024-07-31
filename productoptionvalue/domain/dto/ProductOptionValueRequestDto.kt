package com.bubaum.pairing_server.productoptionvalue.domain.dto

import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.entity.ProductOptionValue

class ProductOptionValueRequestDto(
    val name: String,
    val value: String,
) {
    fun toEntity(productOptionGroup : ProductOptionGroup): ProductOptionValue {
        return ProductOptionValue(
            name = name,
            value = value,
            productOptionGroup=productOptionGroup
        )
    }
}