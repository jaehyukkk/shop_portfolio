package com.bubaum.pairing_server.category.controller

import com.bubaum.pairing_server.category.domain.dto.CategoryAllListResponseDto
import com.bubaum.pairing_server.category.domain.dto.CategoryListResponseDto
import com.bubaum.pairing_server.category.domain.dto.CategoryRequestDto
import com.bubaum.pairing_server.category.domain.dto.CategorySearchDto
import com.bubaum.pairing_server.category.domain.entity.Category
import com.bubaum.pairing_server.category.service.CategoryService
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @Operation(summary = "카테고리 생성 API")
    @PostMapping()
    fun create(@RequestBody categoryRequestDto: CategoryRequestDto) : ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.create(categoryRequestDto))
    }

    @Operation(summary = "카테고리 디테일 API")
    @GetMapping("{idx}")
    fun detail(@PathVariable idx: Long) : ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.entity(idx))
    }

    @Operation(summary = "카테고리 리스트 조회 API")
    @GetMapping()
    fun list() : ResponseEntity<List<CategoryListResponseDto>> {
        return ResponseEntity.ok(categoryService.list())
    }

    @Operation(summary = "상위카테고리 리스트 조회 API")
    @GetMapping("/parent")
    fun parentsList() : ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryService.parentList())
    }


    @Operation(summary = "관리자페이지 카테고리 리스트 조회 API")
    @GetMapping("/all")
    fun allList(pageDto: PageDto, categorySearchDto: CategorySearchDto) : ResponseEntity<Page<CategoryAllListResponseDto>> {
        return ResponseEntity.ok(categoryService.allList(pageDto, categorySearchDto))
    }


    @Operation(summary = "상위 카테고리 아이디로 자식 카테고리 리스트 조회 API")
    @GetMapping("{idx}/children")
    fun getChildrenCategories(@PathVariable idx: Long) : ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryService.getChildrenCategories(idx))
    }
}
