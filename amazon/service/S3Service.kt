package com.bubaum.pairing_server.amazon.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.util.IOUtils
import com.bubaum.pairing_server.amazon.domain.dto.S3ResponseDto
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*

@Service
class S3Service(
    @Value("\${spring.cloud.aws.s3.bucket}") private val bucket: String,
    @Value("\${spring.cloud.aws.s3.folder}") private val folder: String,
    private val s3Client: AmazonS3,
) {

    fun s3Upload(file: MultipartFile) : File {
        val filename = getSaveFileName(file.originalFilename)
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = file.contentType
        try {
            file.inputStream.use { inputStream ->
                val bytes = IOUtils.toByteArray(inputStream)
                objectMetadata.contentLength = bytes.size.toLong()
                val byteArrayInputStream = ByteArrayInputStream(bytes)
                s3Client.putObject(
                    PutObjectRequest(bucket, filename, byteArrayInputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw BaseException(ErrorCode.INTERNAL_SERVER_ERROR)
        }

        return File(
            originalFilename = file.originalFilename,
            path = s3Client.getUrl(bucket, filename).toString(),
            filename = filename
        )
    }

    private fun getSaveFileName(originalFilename: String?): String {
        val extPosIndex: Int? = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)
        return UUID.randomUUID().toString() + "." + ext
    }

    fun getImage(objectKey : String) : S3ResponseDto{
        val s3Object: S3Object = s3Client.getObject(bucket, objectKey)
        val inputStream = s3Object.objectContent
        val metadata = s3Object.objectMetadata
        val contentType = metadata.contentType

        return S3ResponseDto(
            inputStream = inputStream,
            contentType = contentType
        )
    }
}
