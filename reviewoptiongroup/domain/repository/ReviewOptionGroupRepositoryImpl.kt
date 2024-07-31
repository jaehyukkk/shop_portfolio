package com.bubaum.pairing_server.reviewoptiongroup.domain.repository

import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto
import com.bubaum.pairing_server.reviewoptiongroup.domain.entity.QReviewOptionGroup.reviewOptionGroup
import com.bubaum.pairing_server.reviewoptionvalue.domain.dto.ReviewOptionValueListResponseDto
import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.QReviewOptionValue.reviewOptionValue
import com.querydsl.core.group.GroupBy
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ReviewOptionGroupRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ReviewOptionGroupRepositoryCustom {

    override fun getReviewOptionGroupList(productIdx: Long): List<ReviewOptionGroupListResponseDto> {
        return queryFactory.selectFrom(reviewOptionGroup)
            .leftJoin(reviewOptionGroup.reviewOptionValues, reviewOptionValue)
            .where(reviewOptionGroup.product.idx.eq(productIdx))
            .transform(
                GroupBy.groupBy(reviewOptionGroup.idx).list(
                    Projections.constructor(
                        ReviewOptionGroupListResponseDto::class.java,
                        reviewOptionGroup.idx,
                        reviewOptionGroup.name,
                        reviewOptionGroup.required,
                        GroupBy.set(Projections.constructor(
                            ReviewOptionValueListResponseDto::class.java,
                            reviewOptionValue.idx,
                            reviewOptionValue.name,
                        ))
                    )
                )
            )

    }
}