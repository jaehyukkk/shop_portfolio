package com.bubaum.pairing_server.file.service

import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.file.domain.repository.FileRepository
import com.bubaum.pairing_server.utils.global.FileUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val fileRepository: FileRepository
) {

    fun getFile(idx : Long) : File {
        return fileRepository.findById(idx).orElseThrow()
    }

    fun createFile(file: File) : File {
        return fileRepository.save(file)
    }
}
