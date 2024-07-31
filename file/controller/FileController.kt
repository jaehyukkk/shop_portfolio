package com.bubaum.pairing_server.file.controller

import com.bubaum.pairing_server.amazon.domain.dto.S3ResponseDto
import com.bubaum.pairing_server.amazon.service.S3Service
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.file.service.FileService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    private val fileService: FileService,
    private val s3Service: S3Service
) {

    @GetMapping("/{idx}")
    fun getFile(@PathVariable idx: Long) : ResponseEntity<Resource> {
            val file : File = fileService.getFile(idx)
            return getImageResource(file.filename ?: throw BaseException(ErrorCode.ENTITY_NOT_FOUND))
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart(value = "image") image : MultipartFile,
    ) : ResponseEntity<Long> {
        return ResponseEntity.ok(fileService.createFile(s3Service.s3Upload(image)).idx)
    }

    private fun getImageResource(key: String): ResponseEntity<Resource> {
        val s3ResponseVO: S3ResponseDto = s3Service.getImage(key)
        val resource: Resource = InputStreamResource(s3ResponseVO.inputStream)
        return ResponseEntity.ok().contentType(MediaType.valueOf(s3ResponseVO.contentType)).body(resource)
    }
}
