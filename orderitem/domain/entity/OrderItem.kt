package com.bubaum.pairing_server.orderitem.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.product.domain.entity.Product
import com.bubaum.pairing_server.productoptiongroup.domain.entity.ProductOptionGroup
import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.enums.DeliveryCompany
import com.bubaum.pairing_server.enums.OrderStatus
import com.bubaum.pairing_server.order.domain.entity.Order
import com.bubaum.pairing_server.publishcoupon.domain.entity.PublishCoupon
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val count: Int,

    //상품 최종 결제 금액
    var resultAmount: Int? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null,


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_group_id")
    var productOptionGroup: ProductOptionGroup? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    var coupon: PublishCoupon? = null,
    val status: OrderStatus = OrderStatus.CHECKING,

    val invoiceNumber : Long? = null,
    val deliveryCompany : DeliveryCompany? = null,

    ) : Base() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_idx")
    lateinit var order: Order

}