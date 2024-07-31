package com.bubaum.pairing_server.product.controller

import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.product.domain.dto.*
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.product.service.ProductService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart(value = "product") productRequestDto: ProductRequestDto,
        @RequestPart(value = "thumbnail") thumbnail : List<MultipartFile>,
        @RequestPart(value = "images") images : List<MultipartFile>,
        ) : Product {
        return productService.create(productRequestDto, thumbnail, images)
    }

    @GetMapping("{idx}")
    fun detail(@PathVariable idx: Long): ResponseEntity<ProductDetailResponseDto> {
        return ResponseEntity.ok(productService.detail(idx))
    }

    @GetMapping("/modify/{idx}")
    fun getModifyData(@PathVariable idx: Long): ResponseEntity<ProductDetailResponseDto> {
        return ResponseEntity.ok(productService.modifyDetail(idx))
    }

    @GetMapping()
    fun list(productSearchDto: ProductSearchDto, pageDto: PageDto): ResponseEntity<Page<ProductListResponseDto>> {
        return ResponseEntity.ok(productService.list(productSearchDto, pageDto))
    }

    @PutMapping(
        value = ["/{idx}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE]

    )
    fun update(@PathVariable idx: Long,
               @RequestPart(value = "product") productModifyDto: ProductModifyDto,
               @RequestPart(value = "images", required = false) images : List<MultipartFile>?,
               ): ResponseEntity<Void>{
        productService.modify(idx, productModifyDto, images)
        return ResponseEntity.ok().build()
    }

}
