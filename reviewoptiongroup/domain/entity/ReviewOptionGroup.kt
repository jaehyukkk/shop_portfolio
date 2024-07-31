package com.bubaum.pairing_server.reviewoptiongroup.domain.entity

import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.reviewoptionvalue.domain.entity.ReviewOptionValue
import javax.persistence.*

@Entity
class ReviewOptionGroup(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    val product : Product,
    val name: String,
    val required: Boolean,
    val isEnabled: Boolean? = true
) {

    @OneToMany(mappedBy = "reviewOptionGroup", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    val reviewOptionValues: MutableSet<ReviewOptionValue> = LinkedHashSet()
}