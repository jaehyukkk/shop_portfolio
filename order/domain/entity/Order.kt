package com.bubaum.pairing_server.order.domain.entity

import com.bubaum.pairing_server.deliveryinfo.domain.entity.DeliveryInfo
import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.orderitem.domain.entity.OrderItem
import com.bubaum.pairing_server.payment.domain.entity.Payment
import com.bubaum.pairing_server.member.domain.entity.Member
import javax.persistence.*

@Entity
@Table(name = "product_order")
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    val payment: Payment,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_info_idx")
    val deliveryInfo: DeliveryInfo? = null

) : Base(){



    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    val orderItems: MutableSet<OrderItem> = LinkedHashSet()
    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }
}