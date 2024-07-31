package com.bubaum.pairing_server.review.domain.entity

import com.bubaum.pairing_server.global.entity.Base
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.member.domain.entity.Member
import com.bubaum.pairing_server.orderitem.domain.entity.OrderItem
import com.bubaum.pairing_server.product.domain.entity.Product
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE idx = ?")
@Where(clause = "deleted = false")
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long? = null,
    val content: String,
    val rating: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_idx")
    val orderItem: OrderItem,

    val deleted: Boolean = false,
    ): Base() {

    @BatchSize(size = 100)
    @OrderBy("idx asc")
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    var files: MutableSet<File> = LinkedHashSet()

    fun addFile(file: File) {
        files.add(file)
        file.review = this
    }
}
