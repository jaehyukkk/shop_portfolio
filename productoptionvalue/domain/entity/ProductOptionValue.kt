package com.bubaum.pairing_server.productoptionvalue.domain.entity

import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import javax.persistence.*

@Entity
class ProductOptionValue(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,
    val name: String,
    val value: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_group_id")
    var productOptionGroup: ProductOptionGroup
) {


}