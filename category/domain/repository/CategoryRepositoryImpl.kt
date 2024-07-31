package com.bubaum.pairing_server.category.domain.repository

import com.bubaum.pairing_server.category.domain.dto.CategoryAllListResponseDto
import com.bubaum.pairing_server.category.domain.dto.CategorySearchDto
import com.bubaum.pairing_server.category.domain.dto.QCategoryAllListResponseDto
import com.bubaum.pairing_server.category.domain.entity.QCategory.category
import com.bubaum.pairing_server.global.dto.PageDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class CategoryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CategoryRepositoryCustom {

    override fun getCategoryPage(
        pageDto: PageDto,
        categorySearchDto: CategorySearchDto
    ): Page<CategoryAllListResponseDto> {
        val query = queryFactory.select(
            QCategoryAllListResponseDto(
                category.idx,
                category.name,
                category.orderNumber,
                category.isEnabled,
                category.parentIdx
            )
        ).from(category)
            .orderBy(category.idx.desc())
            .offset(pageDto.pageable().offset)
            .limit(pageDto.pageable().pageSize.toLong())
            .fetch()

        val count = queryFactory.select(category.idx.count())
            .from(category)
            .fetchFirst()

        return PageableExecutionUtils.getPage(query, pageDto.pageable()) {count}
    }
}