package com.bubaum.pairing_server.productoptiongroup.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.productoptionvalue.domain.entity.ProductOptionValue
import com.bubaum.pairing_server.product.domain.entity.Product
import javax.persistence.*

@Entity
class ProductOptionGroup(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val idx: Long? = null,
    val addPrice: Int? = 0,
    val stock: Int? = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    val product : Product,
    val isEnabled: Boolean = true
): Base() {

    @OneToMany(mappedBy = "productOptionGroup", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    val productOptionValues: MutableSet<ProductOptionValue> = LinkedHashSet()


}
