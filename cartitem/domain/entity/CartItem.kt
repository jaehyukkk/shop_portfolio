package com.bubaum.pairing_server.cartitem.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.user.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.lang.Boolean
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE cart_item SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val count: Int,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    var product: Product? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_group_idx")
    var productOptionGroup: ProductOptionGroup? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    ) : Base() {

    val isDeleted = Boolean.FALSE
}