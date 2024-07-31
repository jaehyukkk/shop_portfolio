package com.bubaum.pairing_server.category.domain.repository

import com.bubaum.pairing_server.category.domain.dto.CategoryAllListResponseDto
import com.bubaum.pairing_server.category.domain.dto.CategorySearchDto
import com.bubaum.pairing_server.global.dto.PageDto
import org.springframework.data.domain.Page

interface CategoryRepositoryCustom {
    fun getCategoryPage(pageDto: PageDto, categorySearchDto: CategorySearchDto) : Page<CategoryAllListResponseDto>
}