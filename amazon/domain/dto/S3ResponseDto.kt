package com.bubaum.pairing_server.amazon.domain.dto

import com.amazonaws.services.s3.model.S3ObjectInputStream

class S3ResponseDto(
    val inputStream: S3ObjectInputStream,
    val contentType: String
) {
}
