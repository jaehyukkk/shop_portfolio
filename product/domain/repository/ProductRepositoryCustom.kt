package com.bubaum.pairing_server.product.domain.repository

import com.bubaum.pairing_server.product.domain.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepositoryCustom {

    fun getProductPage(productSearchDto: ProductSearchDto, pageable: Pageable) : Page<ProductListResponseDto>

    fun getProductDetail(idx: Long) : ProductDetailResponseDto

    fun modifyProduct(productModifyDto: ProductModifyDto) : Long


}
