package com.bubaum.pairing_server.admin.domain.entity

import com.bubaum.pairing_server.user.domain.entity.User
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Admin(
    @Id
    @Comment("유저 pk")
    val idx: Long? = null,
    val phone: String,
    @OneToOne(fetch = FetchType.LAZY)
    val user : User,
){
}
