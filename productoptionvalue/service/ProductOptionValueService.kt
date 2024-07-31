package com.bubaum.pairing_server.productoptionvalue.service


import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.dto.ProductOptionValueRequestDto
import com.bubaum.pairing_server.productoptionvalue.domain.repository.ProductOptionValueRepository
import org.springframework.stereotype.Service

@Service
class ProductOptionValueService(
    private val productOptionValueRepository: ProductOptionValueRepository
) {

    fun create(productOptionValueRequestDto: ProductOptionValueRequestDto, productOptionGroup: ProductOptionGroup) {
        productOptionValueRepository.save(productOptionValueRequestDto.toEntity(productOptionGroup))
    }
}