package com.bubaum.pairing_server.productoptionvalue.domain.repository

import com.bubaum.pairing_server.productoptionvalue.domain.entity.ProductOptionValue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductOptionValueRepository : JpaRepository<ProductOptionValue, Long> {
}