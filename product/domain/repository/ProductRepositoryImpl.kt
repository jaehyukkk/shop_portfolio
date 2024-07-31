package com.bubaum.pairing_server.product.domain.repository

import com.bubaum.pairing_server.enums.PointStandard
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.file.domain.dto.FileResponseDto
import com.bubaum.pairing_server.file.domain.entity.QFile.file
import com.bubaum.pairing_server.orderitem.domain.entity.QOrderItem.orderItem
import com.bubaum.pairing_server.product.domain.dto.*
import com.bubaum.pairing_server.product.domain.entity.QProduct.product
import com.bubaum.pairing_server.product.enums.ProductSort
import com.bubaum.pairing_server.productoptiongroup.domain.dto.ProductOptionGroupResponseDto
import com.bubaum.pairing_server.productoptiongroup.domain.entity.QProductOptionGroup.productOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.dto.ProductOptionValueResponseDto
import com.bubaum.pairing_server.productoptionvalue.domain.entity.QProductOptionValue.productOptionValue
import com.bubaum.pairing_server.utils.enum.result
import com.querydsl.core.group.GroupBy.*
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringPath
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductRepositoryCustom {

    override fun getProductPage(productSearchDto: ProductSearchDto, pageable: Pageable): Page<ProductListResponseDto> {
        val orderCount = Expressions.stringPath("orderCount")
        val fetch = queryFactory.select(
            QProductListResponseDto(
                product.idx,
                product.name,
                product.price,
                ExpressionUtils.`as`(orderItem.product.idx.count(), "orderCount"),
                product.category.name,
                product.category.idx,
                JPAExpressions.select(file.idx)
                    .from(file)
                    .where(file.isThumbnail.eq(true).and(file.product.idx.eq(product.idx))),
                //TODO: 배송완료된 상품 갯수와 비교해서 결과내기로 바꿔야함
                CaseBuilder()
                    .`when`(product.stock.eq(0))
                    .then(true)
                    .otherwise(false)
            )
        ).from(product)
            .leftJoin(product.category)
            .leftJoin(orderItem).on(orderItem.product.idx.eq(product.idx))
            .where(
                category(productSearchDto)
                , product.isDisplay.eq(true)
                , categoryArray(productSearchDto)
                , priceBetween(productSearchDto))
            .orderBy(*dynamicSort(productSearchDto.sort, orderCount).toTypedArray())
            .groupBy(product.idx)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(product.idx.count())
            .from(product)
            .leftJoin(product.category)
            .where(
                category(productSearchDto)
                , product.isDisplay.eq(true)
                , categoryArray(productSearchDto)
                , priceBetween(productSearchDto))
            .groupBy(product.idx)
            .fetchFirst()

        return PageableExecutionUtils.getPage(fetch, pageable) {count}
    }


    override fun getProductDetail(idx: Long) : ProductDetailResponseDto {
        val product = queryFactory.selectFrom(product)
            .where(product.idx.eq(idx))
            .leftJoin(product.files, file)
            .leftJoin(product.category)
            .orderBy(file.idx.asc())
            .transform(
                groupBy(product.idx).list(Projections.constructor(
                    ProductDetailResponseDto::class.java
                    , product.idx
                    , product.name
                    , product.price
                    , product.category.name
                    , product.category.parentIdx
                    , product.category.idx
                    //TODO: 배송완료된 상품 갯수와 비교해서 결과내기로 바꿔야함
                    , CaseBuilder()
                        .`when`(product.stock.eq(0))
                        .then(true)
                        .otherwise(false)
                    , product.description
                    , product.pointStandard
                    , product.point
                    , product.orderNum
                    , product.isDisplay
                    , list(Projections.constructor(
                        FileResponseDto::class.java
                        , file.idx
                        , file.path
                        , file.originalFilename
                        , file.filename
                        , file.isThumbnail
                    ))
                )))?.first() ?: throw BaseException(ErrorCode.ENTITY_NOT_FOUND)


        val productOptionGroups : List<ProductOptionGroupResponseDto> = queryFactory.selectFrom(productOptionGroup)
            .leftJoin(productOptionGroup.productOptionValues, productOptionValue)
            .where(productOptionGroup.product.idx.eq(idx), productOptionGroup.isEnabled.eq(true))
            .transform(
                groupBy(productOptionGroup.idx).list(Projections.constructor(
                    ProductOptionGroupResponseDto::class.java
                    , productOptionGroup.idx
                    , productOptionGroup.stock
                    , productOptionGroup.addPrice
                    , set(Projections.constructor(
                        ProductOptionValueResponseDto::class.java
                        , productOptionValue.idx
                        , productOptionValue.name
                        , productOptionValue.value
                    ))
                )))

        product.optionGroups = productOptionGroups

        return product
    }


//    val categoryIdx: Long,
//    val name: String,
//    val description: String,
//    val pointStandard: PointStandard,
//    val point: Int,
//    val orderNum: Int,
//    val price: Int,
//    val isDisplay: Boolean,
    @Transactional
    override fun modifyProduct(productModifyDto: ProductModifyDto): Long {
        return queryFactory.update(product)
            .set(product.name, productModifyDto.name)
            .set(product.description, productModifyDto.description)
            .set(product.pointStandard, productModifyDto.pointStandard)
            .set(product.point, productModifyDto.point)
            .set(product.orderNum, productModifyDto.orderNum)
            .set(product.price, productModifyDto.price)
            .set(product.isDisplay, productModifyDto.isDisplay)
            .set(product.category, productModifyDto.category)
            .where(product.idx.eq(productModifyDto.idx))
            .execute()
    }

    private fun category(productSearchDto: ProductSearchDto): BooleanExpression? {
        if(productSearchDto.categoryIdx == null && productSearchDto.subCategoryIdx == null){
            return null
        }
        if (productSearchDto.subCategoryIdx != null) {
            return product.category.idx.eq(productSearchDto.subCategoryIdx)
        }
        //메인카테고리로만 검색 한 상황
        return product.category.idx.eq(productSearchDto.categoryIdx)
            .or(product.category.parentIdx.eq(productSearchDto.categoryIdx))
    }

    private fun categoryArray(productSearchDto: ProductSearchDto): BooleanExpression? {
        if(productSearchDto.categoryIdxs != null) {
            return product.category.idx.`in`(productSearchDto.categoryIdxs)
        }

        return null
    }

    fun dynamicSort(sort: ProductSort?, orderCount: StringPath): List<OrderSpecifier<*>> {
        val orderList: MutableList<OrderSpecifier<*>> = ArrayList()
        //최신순
        if (sort == null) {
            orderList.add(OrderSpecifier(Order.DESC, product.idx))
        }
        //높은가격순
        if (sort == ProductSort.HIGH_PRICE) {
            orderList.add(OrderSpecifier(Order.DESC, product.price))
        }
        //낮은가격순
        if (sort == ProductSort.LOW_PRICE){
            orderList.add(OrderSpecifier(Order.ASC, product.price))
        }
        //판매량순
        if(sort == ProductSort.POPULAR){
            orderList.add(OrderSpecifier(Order.DESC, orderCount))
        }

        orderList.add(OrderSpecifier(Order.DESC, product.idx))
        return orderList
    }

    fun price(productSearchDto: ProductSearchDto): BooleanExpression? {
        if(productSearchDto.startPrice == null && productSearchDto.endPrice == null) {
            return null
        }
        if(productSearchDto.startPrice == null) {
            return product.price.loe(productSearchDto.endPrice)
        }
        if(productSearchDto.endPrice == null) {
            return product.price.goe(productSearchDto.startPrice)
        }
        return product.price.between(productSearchDto.startPrice, productSearchDto.endPrice)
    }

    fun priceBetween(productSearchDto: ProductSearchDto): BooleanExpression? {
        if (productSearchDto.priceBetweens == null) {
            return null
        }

        var query: BooleanExpression = Expressions.asString("1").eq("1")

        productSearchDto.priceBetweens.forEachIndexed{ index, priceBetween ->
            val arr = priceBetween.split("|")
            val startPrice = arr[0].toInt()
            val endPrice = arr[1].toInt()
            query = if (index == 0) {
                query.and(product.price.between(startPrice, endPrice))
            } else {
                query.or(product.price.between(startPrice, endPrice))
            }
        }

        return query
    }
}
