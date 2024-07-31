package com.bubaum.pairing_server.global.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Base {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("생성일")
    open var createDate: LocalDateTime = LocalDateTime.MIN


    @LastModifiedDate
    @Column(nullable = true)
    @Comment("수정일")
    protected open var modDate: LocalDateTime = LocalDateTime.MIN
}