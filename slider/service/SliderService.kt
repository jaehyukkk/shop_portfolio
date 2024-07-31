package com.bubaum.pairing_server.slider.service

import com.bubaum.pairing_server.amazon.service.S3Service
import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.coupon.domain.repository.CouponRepository
import com.bubaum.pairing_server.coupon.service.CouponService
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.file.domain.repository.FileRepository
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.slider.domain.dto.SliderDetailResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderPageListResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderRequestDto
import com.bubaum.pairing_server.slider.domain.repository.SliderRepository
import com.bubaum.pairing_server.slider.enums.SliderType
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class SliderService(
    private val sliderRepository: SliderRepository,
    private val s3Service: S3Service,
    private val fileRepository : FileRepository,
    private val couponService: CouponService
) {

    @Transactional
    fun create(sliderRequestDto: SliderRequestDto, multipartFile: MultipartFile) {
        val file = fileRepository.save(s3Service.s3Upload(multipartFile))
        if(sliderRequestDto.sliderType == SliderType.COUPON_EVENT) {
            val coupon = couponService.getCoupon(
                sliderRequestDto.couponIdx ?: throw BaseException(ErrorCode.BAD_REQUEST)
            )
            sliderRepository.save(sliderRequestDto.toEntity(file, coupon))
            return
        }

        sliderRepository.save(sliderRequestDto.toEntity(file, null))
    }

    fun list() = sliderRepository.getSliderList()

    fun detail(idx :Long) : SliderDetailResponseDto{
        return sliderRepository.getSliderDetail(idx)
    }

    fun getSlider(idx : Long) = sliderRepository.findById(idx).orElseThrow { BaseException(ErrorCode.NOT_FOUND_ORDER) }

    @Transactional
    fun modify(idx: Long, sliderRequestDto: SliderRequestDto, multipartFile: MultipartFile?) {
        val file : File = if (multipartFile != null) {
            fileRepository.save(s3Service.s3Upload(multipartFile))
        } else {
            fileRepository.findById(getSlider(idx).file.idx!!).orElseThrow{
                BaseException(ErrorCode.NOT_FOUND_ORDER)
            }
        }

        var coupon : Coupon? = null
        if(sliderRequestDto.sliderType == SliderType.COUPON_EVENT) {
            coupon = couponService.getCoupon(
                sliderRequestDto.couponIdx ?: throw BaseException(ErrorCode.BAD_REQUEST)
            )
        }

        val modifyResult = sliderRepository.modifySlider(
            idx = idx,
            sliderRequestDto = sliderRequestDto,
            file = file,
            coupon = coupon
        )

        if (modifyResult != 1) {
            throw BaseException(ErrorCode.NOT_FOUND_ORDER)
        }
    }

    fun delete(idx: Long) {
        sliderRepository.deleteById(idx)
    }

    fun page(pageDto: PageDto) : Page<SliderPageListResponseDto>{
        return sliderRepository.getSliderPageList(pageDto.pageable())
    }
}
