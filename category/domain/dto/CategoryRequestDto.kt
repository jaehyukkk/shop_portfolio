package com.bubaum.pairing_server.category.domain.dto

import com.bubaum.pairing_server.category.domain.entity.Category
import io.swagger.v3.oas.annotations.media.Schema

class CategoryRequestDto(
    @Schema(description = "카테고리명", defaultValue = "아우터")
    val name: String,
//    @Schema(description = "카테고리 설명", defaultValue = "설명..")
//    val description: String? = null,
    @Schema(description = "카테고리 노출 순서(작을수록 앞)", defaultValue = "1")
    val orderNumber: Int,
    @Schema(description = "사용여부", defaultValue = "true")
    val isEnabled: Boolean = true,
    @Schema(description = "하위카테고리 생성시 부모카테고리 idx", defaultValue = "")
    val parentIdx: Long? = null
) {

    fun toEntity(): Category {
        return Category(
            name = name,
//            description = description,
            orderNumber = orderNumber,
            isEnabled = isEnabled,
            parentIdx = parentIdx
        )
    }

    companion object{
        fun of(category: Category): CategoryRequestDto {
            return CategoryRequestDto(
                name = category.name,
//                description = category.description,
                orderNumber = category.orderNumber,
                isEnabled = category.isEnabled,
                parentIdx = category.parentIdx
            )
        }
    }
}