package com.bubaum.pairing_server.productoptiongroup.domain.repository

import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ProductOptionGroupRepository : JpaRepository<ProductOptionGroup, Long>{

    @Transactional
    @Modifying
    @Query("update ProductOptionGroup pog set pog.stock = pog.stock - :stock where pog.idx = :productOptionGroupIdx")
    fun updateStock(productOptionGroupIdx: Long, stock: Int)

    @Transactional
    @Modifying
    @Query("update ProductOptionGroup pog set pog.stock = pog.stock + :stock where pog.idx = :productOptionGroupIdx")
    fun updateStockPlus(productOptionGroupIdx: Long, stock: Int)

    @Transactional
    @Modifying
    @Query("update ProductOptionGroup pog set pog.stock = :stock, pog.addPrice = :addPrice where pog.idx = :productOptionGroupIdx")
    fun updateProductOptionGroup(productOptionGroupIdx: Long, stock: Int, addPrice: Int)

    @Transactional
    @Modifying
    @Query("update ProductOptionGroup pog set pog.isEnabled = false where pog.product = :product")
    fun disableProductOptionGroup(product: Product)
}
