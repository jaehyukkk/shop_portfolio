package com.bubaum.pairing_server.productoptiongroup.service

import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.dto.ProductOptionGroupRequestDto
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.productoptiongroup.domain.repository.ProductOptionGroupRepository
import org.springframework.stereotype.Service

@Service
class ProductOptionGroupService(
    private val productOptionGroupRepository: ProductOptionGroupRepository
) {

    fun create(productOptionGroupRequestDto: ProductOptionGroupRequestDto, product: Product) : ProductOptionGroup {
        return productOptionGroupRepository.save(productOptionGroupRequestDto.toEntity(product))
    }

    fun getProductOptionGroup(idx: Long): ProductOptionGroup {
        return productOptionGroupRepository.findById(idx).orElseThrow { throw Exception("존재하지 않는 옵션 그룹입니다.") }
    }

    fun updateProductOptionGroup(productOptionGroupIdx: Long, stock: Int, addPrice: Int) {
        productOptionGroupRepository.updateProductOptionGroup(productOptionGroupIdx, stock, addPrice)
    }

    fun disableProductOptionGroup(product: Product) {
        productOptionGroupRepository.disableProductOptionGroup(product)
    }
}
