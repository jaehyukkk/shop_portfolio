package com.bubaum.pairing_server.category.domain.repository

import com.bubaum.pairing_server.category.domain.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> ,CategoryRepositoryCustom {

    fun findAllByOrderByOrderNumberAsc() : List<Category>

    fun findByParentIdx(parentIdx: Long) : List<Category>

    fun findAllByParentIdxIsNull() : List<Category>
}