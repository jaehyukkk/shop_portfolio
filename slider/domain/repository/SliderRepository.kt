package com.bubaum.pairing_server.slider.domain.repository

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.slider.domain.dto.SliderRequestDto
import com.bubaum.pairing_server.slider.domain.entity.Slider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SliderRepository : JpaRepository<Slider, Long>, SliderRepositoryCustom{

    @Transactional
    @Modifying
    @Query("""
        UPDATE Slider s SET 
        s.title = :#{#sliderRequestDto.title},
        s.description = :#{#sliderRequestDto.description},
        s.file = :file,
        s.sliderType = :#{#sliderRequestDto.sliderType},
        s.url = :#{#sliderRequestDto.url},
        s.orderNum = :#{#sliderRequestDto.orderNum},
        s.status = :#{#sliderRequestDto.status},
        s.coupon = :coupon
        WHERE s.idx = :idx""")
    fun modifySlider(idx: Long, sliderRequestDto: SliderRequestDto, file: File, coupon: Coupon?) : Int
}
