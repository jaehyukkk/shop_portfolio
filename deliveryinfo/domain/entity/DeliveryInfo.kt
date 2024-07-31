package com.bubaum.pairing_server.deliveryinfo.domain.entity

import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@SQLDelete(sql = "UPDATE delivery_info SET is_deleted = true WHERE idx = ?")
@Where(clause = "is_deleted = false")
@Entity
class DeliveryInfo(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    var idx: Long? = null,
    val deliveryInfoName: String,
    val name: String,
    val phone: String,
    val addr: String,
    val detailAddr: String,
    val postCode: String,
    var isDefault: Boolean = false,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User
) {
    val isDeleted = java.lang.Boolean.FALSE
}