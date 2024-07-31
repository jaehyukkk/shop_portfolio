package com.bubaum.pairing_server.product.domain.dto

import com.bubaum.pairing_server.product.enums.ProductSort
import io.swagger.v3.oas.annotations.media.Schema

class ProductSearchDto(
    val categoryIdx: Long? = null,
    val subCategoryIdx: Long? = null,
    val categoryIdxs: List<Long>? = null,
    @Schema(description = "정렬기준")
    val sort : ProductSort? = null,
    @Schema(description = "가격 시작", defaultValue = "0")
    val startPrice: Int? = null,
    @Schema(description = "가격 끝", defaultValue = "0")
    val endPrice: Int? = null,
    @Schema(description = "", defaultValue = "")
    val priceBetweens: List<String>? = null
) {
}
