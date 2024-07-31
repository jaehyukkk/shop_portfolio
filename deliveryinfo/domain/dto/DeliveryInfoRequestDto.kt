package com.bubaum.pairing_server.deliveryinfo.domain.dto

import com.bubaum.pairing_server.deliveryinfo.domain.entity.DeliveryInfo
import com.bubaum.pairing_server.user.domain.entity.User

class DeliveryInfoRequestDto(
    val deliveryInfoName: String? = null,
    val name: String? = null,
    val phone1: String? = null,
    val phone2: String? = null,
    val phone3: String? = null,
    val addr: String? = null,
    val detailAddr: String? = null,
    val postCode: String? = null,
    val isDefault: Boolean? = false
) {

    fun toEntity(user : User) : DeliveryInfo {
        return DeliveryInfo(
            deliveryInfoName = deliveryInfoName!!,
            name = name!!,
            phone = phone1!! + phone2!! + phone3!!,
            addr = addr!!,
            detailAddr = detailAddr!!,
            user = user,
            postCode = postCode!!,
            isDefault = isDefault!!
        )
    }
}