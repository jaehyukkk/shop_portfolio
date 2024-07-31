package com.bubaum.pairing_server.category.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx : Long? = null,
    val name: String,
//    val description: String? = null,
    val orderNumber: Int,
    val isEnabled: Boolean = true,
    val parentIdx: Long? = null
) : Base(){
}