package com.bubaum.pairing_server.file.domain.repository

import com.bubaum.pairing_server.file.domain.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface FileRepository : JpaRepository<File, Long>{

    @Transactional
    @Modifying
    @Query("update File f set f.isThumbnail = false where f.product.idx = :productIdx and f.isThumbnail = true")
    fun updateThumbnailFalse(productIdx: Long)

    @Transactional
    @Modifying
    @Query("update File f set f.isThumbnail = true where f.idx = :fileIdx")
    fun updateThumbnailTrue(fileIdx: Long)

    @Transactional
    @Modifying
    @Query("delete from File f where f.idx in :fileIdxs")
    fun deleteFiles(fileIdxs: List<Long>)
}
