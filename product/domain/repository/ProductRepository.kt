package com.bubaum.pairing_server.product.domain.repository

import com.bubaum.pairing_server.product.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom {
}