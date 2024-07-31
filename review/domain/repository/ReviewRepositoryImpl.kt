package com.bubaum.pairing_server.review.domain.repository

import com.bubaum.pairing_server.file.domain.dto.FileResponseDto
import com.bubaum.pairing_server.file.domain.entity.QFile.file
import com.bubaum.pairing_server.orderitem.domain.entity.QOrderItem.orderItem
import com.bubaum.pairing_server.product.domain.entity.QProduct.product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.QProductOptionGroup.productOptionGroup
import com.bubaum.pairing_server.productoptionvalue.domain.entity.QProductOptionValue.productOptionValue
import com.bubaum.pairing_server.review.domain.dto.QReviewOptionStatisticsQuerydslDto
import com.bubaum.pairing_server.review.domain.dto.ReviewOptionStatisticsQuerydslDto
import com.bubaum.pairing_server.review.domain.dto.ReviewResponseDto
import com.bubaum.pairing_server.review.domain.entity.QReview.review
import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.QReviewOptionGroup.reviewOptionGroup
import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.QReviewOptionValue.reviewOptionValue
import com.bubaum.pairing_server.reviewoptionvaluereview.domain.entity.QReviewOptionValueReview.reviewOptionValueReview
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.set
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ReviewRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ReviewRepositoryCustom {


//    val idx: Long,
//    val content: String,
//    val rating: Int,
//    val memberIdx: Long,
//    val memberId: String,
//    val orderItemOption: String,
//    val reviewOption: String,
//    val createdDate: LocalDateTime,
//    val images: List<Long>
    override fun getReviewList(productIdx: Long): List<ReviewResponseDto> {
    return queryFactory.selectFrom(review)
        .leftJoin(review.member.user)
        .leftJoin(review.orderItem, orderItem)
        .leftJoin(review.product, product)
        .leftJoin(orderItem.productOptionGroup, productOptionGroup)
        .leftJoin(reviewOptionValueReview).on(reviewOptionValueReview.review.idx.eq(review.idx))
        .leftJoin(reviewOptionValueReview.reviewOptionValue)
        .leftJoin(reviewOptionGroup).on(
            reviewOptionGroup.product.idx.eq(product.idx)
                .and(reviewOptionGroup.idx.eq(reviewOptionValueReview.reviewOptionValue.reviewOptionGroup.idx))
        )
        .leftJoin(review.files, file)
        .where(product.idx.eq(productIdx))
        .groupBy(review.idx)
        .transform(
            groupBy(review.idx).list(
                Projections.constructor(
                    ReviewResponseDto::class.java,
                    review.idx,
                    review.content,
                    review.rating,
                    review.member.idx,
                    review.member.user.id,
                    JPAExpressions.select(
                        Expressions.stringTemplate(
                            "GROUP_CONCAT({0})",
                            productOptionValue.value
                        )
                    ).from(productOptionValue)
                        .where(productOptionValue.productOptionGroup.idx.eq(orderItem.productOptionGroup.idx))
                        .groupBy(productOptionValue.productOptionGroup.idx),
                    Expressions.stringTemplate(
                        "GROUP_CONCAT({0}, ':', {1})",
                        reviewOptionGroup.name,
                        reviewOptionValueReview.reviewOptionValue.name
                    ),
                    review.createDate,
                    set(
                        Projections.constructor(
                            FileResponseDto::class.java,
                            file.idx,
                            file.path,
                            file.originalFilename,
                            file.filename,
                            file.isThumbnail
                        )
                    )
                )
            )
        )


    }


    override fun getReviewOptionStatistics(productIdx: Long): List<ReviewOptionStatisticsQuerydslDto> {
        return queryFactory.select(
            QReviewOptionStatisticsQuerydslDto(
                reviewOptionGroup.name,
                reviewOptionValueReview.reviewOptionValue.name,
                reviewOptionValueReview.reviewOptionValue.idx.count()
            )
        ).from(review)
            .leftJoin(reviewOptionValueReview).on(reviewOptionValueReview.review.idx.eq(review.idx))
            .leftJoin(reviewOptionValueReview.reviewOptionValue, reviewOptionValue)
            .leftJoin(reviewOptionValue.reviewOptionGroup, reviewOptionGroup)
            .where(review.product.idx.eq(productIdx))
            .groupBy(reviewOptionValue.idx)
            .fetch()
    }

    override fun getRatingList(productIdx: Long): Double {
        return queryFactory.select(review.rating.avg())
            .from(review)
            .where(review.product.idx.eq(productIdx))
            .fetchOne() ?: 0.0
    }
}