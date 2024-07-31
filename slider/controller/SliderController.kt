package com.bubaum.pairing_server.slider.controller

import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.slider.domain.dto.SliderDetailResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderListResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderPageListResponseDto
import com.bubaum.pairing_server.slider.domain.dto.SliderRequestDto
import com.bubaum.pairing_server.slider.service.SliderService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/v1/slider")
@RestController
class SliderController(
    private val sliderService: SliderService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart(value = "slider") sliderRequestDto: SliderRequestDto,
        @RequestPart(value = "file") file : MultipartFile
    ) : ResponseEntity<Void>{
        sliderService.create(sliderRequestDto, file)
        return ResponseEntity.ok().build()
    }

    @GetMapping()
    fun list() : ResponseEntity<List<SliderListResponseDto>> {
        return ResponseEntity.ok(sliderService.list())
    }

    @GetMapping("/{idx}")
    fun detail(@PathVariable idx : Long) : ResponseEntity<SliderDetailResponseDto> {
        return ResponseEntity.ok(sliderService.detail(idx))
    }

    @PutMapping("/{idx}")
    fun update(
        @PathVariable idx : Long,
        @RequestPart(value = "slider") sliderRequestDto: SliderRequestDto,
        @RequestPart(value = "file", required = false) file : MultipartFile?
    ) : ResponseEntity<Void>{
        sliderService.modify(idx, sliderRequestDto, file)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{idx}")
    fun delete(@PathVariable idx : Long) : ResponseEntity<Void> {
        sliderService.delete(idx)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/page")
    fun pageList(pageDto: PageDto) : ResponseEntity<Page<SliderPageListResponseDto>> {
        return ResponseEntity.ok(sliderService.page(pageDto))
    }
}
