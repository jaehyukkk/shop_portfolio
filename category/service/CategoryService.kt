package com.bubaum.pairing_server.category.service

import com.bubaum.pairing_server.category.domain.dto.*
import com.bubaum.pairing_server.category.domain.entity.Category
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.category.domain.repository.CategoryRepository
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun create(categoryRequestDto: CategoryRequestDto) : Category {
        return categoryRepository.save(categoryRequestDto.toEntity())
    }

    fun allList(pageDto: PageDto, categorySearchDto: CategorySearchDto): Page<CategoryAllListResponseDto> {
        return categoryRepository.getCategoryPage(pageDto, categorySearchDto)
    }

    fun list() : List<CategoryListResponseDto>{
        val categories = categoryRepository.findAllByOrderByOrderNumberAsc()
        val dtoList = categories
            .filter { it.parentIdx == null }
            .map { category ->
                CategoryListResponseDto(
                    idx = category.idx!!,
                    name = category.name,
                    childrenCategory = categories
                        .filter { it.parentIdx == category.idx }
                        .map {
                            ChildrenCategoryListResponseDto(
                                idx = it.idx!!,
                                name = it.name,
                                parentIdx = it.parentIdx!!
                            )
                        }
                )
            }

        return dtoList.toList()
    }

    fun entity(idx : Long) : Category {
        return categoryRepository.findById(idx).orElseThrow { BaseException(ErrorCode.ENTITY_NOT_FOUND) }
    }

    fun getChildrenCategories(idx: Long) : List<Category>{
        return categoryRepository.findByParentIdx(idx)
    }

    fun parentList() : List<Category>{
        return categoryRepository.findAllByParentIdxIsNull()
    }
}
