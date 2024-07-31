package com.bubaum.pairing_server.slider.domain.repository

import com.bubaum.pairing_server.slider.domain.dto.SliderDetailResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderListResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderPageListResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SliderRepositoryCustom {

    fun getSliderList() : List<SliderListResponseDto>

    fun getSliderDetail(idx : Long) : SliderDetailResponseDto

    fun getSliderPageList(pageable: Pageable) : Page<SliderPageListResponseDto>
}
