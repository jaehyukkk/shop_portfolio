package com.bubaum.pairing_server.file.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.review.domain.entity.Review
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class File(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    var path: String? = null,
    var originalFilename: String? = null,
    var filename: String? = null,
    var isThumbnail: Boolean = false
): Base() {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product : Product? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    var review : Review? = null
}