package com.bubaum.pairing_server.product.service

import com.bubaum.pairing_server.amazon.service.S3Service
import com.bubaum.pairing_server.category.service.CategoryService
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.file.domain.repository.FileRepository
import com.bubaum.pairing_server.product.domain.dto.*
import com.bubaum.pairing_server.product.domain.repository.ProductRepository
import com.bubaum.pairing_server.productoptiongroup.service.ProductOptionGroupService
import com.bubaum.pairing_server.productoptionvalue.service.ProductOptionValueService
import com.bubaum.pairing_server.reviewoptiongroup.service.ReviewOptionGroupService
import com.bubaum.pairing_server.utils.global.FileUtil
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.security.NoSuchAlgorithmException

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val fileRepository: FileRepository,
    private val categoryService: CategoryService,
    private val productOptionValueService: ProductOptionValueService,
    private val productOptionGroupService: ProductOptionGroupService,
    private val reviewOptionGroupService: ReviewOptionGroupService,
    private val s3Service: S3Service
) {

    @Transactional
    fun create(productRequestDto: ProductRequestDto, thumbnail : List<MultipartFile>, images : List<MultipartFile>) : Product {
        val product = productRepository.save(productRequestDto.toEntity(categoryService.entity(productRequestDto.categoryIdx)))

        for(optionGroup in productRequestDto.productOptionGroups.orEmpty()){
            val productOptionGroup = productOptionGroupService.create(optionGroup, product)
            for(option in optionGroup.productOptionValues.orEmpty()){
                productOptionValueService.create(option, productOptionGroup)
            }
        }

        for(reviewOptionGroup in productRequestDto.reviewOptionGroups.orEmpty()){
            reviewOptionGroupService.create(reviewOptionGroup, product)
        }

        saveFile(thumbnail, true, product)
        saveFile(images, false, product)
        return product
    }

    fun list(productSearchDto: ProductSearchDto, pageDto: PageDto) : Page<ProductListResponseDto> {
        return productRepository.getProductPage(productSearchDto, pageDto.pageable())
    }

    fun detail(idx: Long) : ProductDetailResponseDto {
        return productRepository.getProductDetail(idx)
    }

    fun modifyDetail(idx: Long) :  ProductDetailResponseDto{
        val detail = productRepository.getProductDetail(idx)
        detail.reviewOptionGroups = reviewOptionGroupService.getReviewOptionGroupList(idx)

        return detail
    }

    @Transactional
    fun modify(idx: Long, productModifyDto: ProductModifyDto, images : List<MultipartFile>?) {
        //TODO: 이미지 수정기능
        //TODO: 카테고리 수정기능

        val product = getProduct(idx)
        productModifyDto.idx = idx
        productModifyDto.category = categoryService.entity(productModifyDto.categoryIdx)
        productRepository.modifyProduct(productModifyDto)
        //이미지 수정
        fileRepository.updateThumbnailFalse(productModifyDto.idx!!)

        images?.forEachIndexed { index, it ->
            saveFile(it, productModifyDto.thumbnailIndex == index, product)
        }
        if (productModifyDto.thumbnailIdx != null) {
            fileRepository.updateThumbnailTrue(productModifyDto.thumbnailIdx)
        }
        if (!productModifyDto.removeImages.isNullOrEmpty()) {
            fileRepository.deleteFiles(productModifyDto.removeImages)
        }

        //옵션그룹 수정
        if(productModifyDto.isNewOptionGroup){
            //새로운 옵션그룹 생성
            productOptionGroupService.disableProductOptionGroup(product)
            for(optionGroup in productModifyDto.productOptionGroups.orEmpty()){
                val productOptionGroup = productOptionGroupService.create(optionGroup, getProduct(idx))
                for(option in optionGroup.productOptionValues.orEmpty()){
                    productOptionValueService.create(option, productOptionGroup)
                }
            }
        } else {
            //기존 옵션그룹 수정
            for(optionGroup in productModifyDto.productOptionGroups.orEmpty()){
                println("OPTION_GROUPS")
                productOptionGroupService.updateProductOptionGroup(
                    productOptionGroupIdx = optionGroup.idx!!,
                    addPrice = optionGroup.addPrice!!,
                    stock = optionGroup.stock!!
                )
            }
        }


    }

    fun getProduct(idx: Long) : Product {
        return productRepository.findById(idx).orElseThrow()
    }

    @Throws(IOException::class, NoSuchAlgorithmException::class)
    private fun saveFile(multipartFiles: List<MultipartFile>, isThumbnail: Boolean, product: Product) {
        for (multipartFile in multipartFiles) {
            val file: File = s3Service.s3Upload(multipartFile)
            file.product = product
            file.isThumbnail = isThumbnail
            val createFile = fileRepository.save(file)
            product.addFile(createFile)
        }
    }

    @Throws(IOException::class, NoSuchAlgorithmException::class)
    private fun saveFile(multipartFile: MultipartFile, isThumbnail: Boolean, product: Product) {
        val file: File = s3Service.s3Upload(multipartFile)
        file.product = product
        file.isThumbnail = isThumbnail
        val createFile = fileRepository.save(file)
        product.addFile(createFile)
    }


}
