package com.bubaum.pairing_server.slider.domain.repository

import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.slider.domain.dto.*
import com.bubaum.pairing_server.slider.domain.entity.QSlider.slider
import com.bubaum.pairing_server.slider.enums.SliderType
import com.bubaum.pairing_server.utils.enum.result
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class SliderRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : SliderRepositoryCustom{

    override fun getSliderList(): List<SliderListResponseDto> {
        return queryFactory.select(
            QSliderListResponseDto(
                slider.idx,
                slider.file.idx,
                slider.sliderType,
                slider.url,
                slider.coupon?.idx
            )
        ).from(slider)
            .where(slider.status.eq(true))
            .leftJoin(slider.coupon)
            .leftJoin(slider.file)
            .orderBy(slider.orderNum.asc(), slider.idx.desc())
            .fetch()
    }

    override fun getSliderDetail(idx: Long): SliderDetailResponseDto {
        return queryFactory.select(
            QSliderDetailResponseDto(
                slider.idx,
                slider.title,
                slider.description,
                slider.file.idx,
                slider.file.originalFilename,
                slider.sliderType,
                slider.url,
                slider.coupon?.idx,
                slider.coupon?.name
            )
        ).from(slider)
            .where(slider.idx.eq(idx))
            .leftJoin(slider.coupon)
            .leftJoin(slider.file)
            .fetchOne() ?: throw BaseException(ErrorCode.BAD_REQUEST)
    }


//    val idx: Long,
//    val title: String,
//    val sliderType: SliderType,
//    val couponName: String? = null


    override fun getSliderPageList(pageable: Pageable): Page<SliderPageListResponseDto> {
        val query = queryFactory.select(
            QSliderPageListResponseDto(
                slider.idx,
                slider.title,
                slider.sliderType,
                slider.coupon?.name,
                slider.orderNum,
                slider.status
            )
        ).from(slider)
            .leftJoin(slider.coupon)
            .leftJoin(slider.file)
            .orderBy(slider.idx.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory.select(slider.idx.count())
            .from(slider)
            .fetchFirst()

        return PageableExecutionUtils.getPage(query, pageable) { count }
    }
}
