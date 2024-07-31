package com.bubaum.pairing_server.slider.domain.entity

import com.bubaum.pairing_server.coupon.domain.entity.Coupon
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.slider.enums.SliderType
import com.bubaum.pairing_server.slider.enums.UrlType
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE slider SET deleted = true WHERE idx = ?")
@Where(clause = "deleted = false")
class Slider(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,

    val title: String,
    val description: String? = null,

    @JsonIgnore
    @OneToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "file_idx")
    val file : File,
    val sliderType: SliderType,

    val url: String? = null,

    val status: Boolean = true,

    val orderNum: Int = 0,
    @JsonIgnore
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @JoinColumn(name = "coupon_idx")
    val coupon: Coupon? = null,

    val deleted : Boolean = false,
): Base() {
}
