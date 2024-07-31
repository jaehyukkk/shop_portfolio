package com.bubaum.pairing_server.file.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class FileResponseDto @QueryProjection constructor(
    val idx: Long,
    var path: String,
    var originalFilename: String,
    var filename: String,
    var isThumbnail: Boolean
) {
}