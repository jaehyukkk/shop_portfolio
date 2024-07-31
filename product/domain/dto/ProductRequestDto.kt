package com.bubaum.pairing_server.product.domain.dto

import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupRequestDto
import com.bubaum.pairing_server.category.domain.entity.Category
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.enums.PointStandard
import com.bubaum.pairing_server.productoptiongroup.domain.dto.ProductOptionGroupRequestDto
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema

class ProductRequestDto(
    @Schema(description = "상품명", defaultValue = "잠바")
    val name: String,
    @Schema(description = "상품설명", defaultValue = "설명")
    val description: String? = null,
    @Schema(description = "가격", defaultValue = "30000")
    val price: Int,
    @Schema(description = "재고", defaultValue = "100")
    val stock: Int,
    @Schema(description = "노출여부", defaultValue = "true")
    val isDisplay: Boolean = true,
    @Schema(description = "카테고리 idx", defaultValue = "1")
    val categoryIdx: Long,
    @Schema(description = "포인트 (퍼센트비율)", defaultValue = "5")
    val point: Int,
    @Schema()
    val pointStandard: PointStandard,

    @Schema(description = "노출순서", defaultValue = "1")
    val orderNum: Int,

    val productOptionGroups: List<ProductOptionGroupRequestDto>? = null,
    val reviewOptionGroups: List<ReviewOptionGroupRequestDto>? = null

) {
    @JsonIgnore
    var category: Category? = null

    fun toEntity(category: Category): Product {
        return Product(
            name = name,
            description = description,
            price = price,
            stock = stock,
            isDisplay = isDisplay,
            point = point,
            pointStandard = pointStandard,
            orderNum = orderNum
        ).apply {
            this.category = category
        }
    }
}
