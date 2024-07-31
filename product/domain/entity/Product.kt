package com.bubaum.pairing_server.product.domain.entity

import com.bubaum.pairing_server.category.domain.entity.Category
import com.bubaum.pairing_server.review.domain.entity.Review
import com.bubaum.pairing_server.enums.PointStandard
import com.bubaum.pairing_server.file.domain.entity.File
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE idx = ?")
@Where(clause = "deleted = false")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx : Long? = null,
    val name: String,
    val description: String? = null,
    val isDisplay: Boolean = true,
    val price: Int,
    val stock: Int,
    val point: Int,
    val pointStandard: PointStandard,
    val orderNum: Int,
    val deleted: Boolean = false,
) {
    @OrderBy("idx desc")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    var reviews: MutableSet<Review> = LinkedHashSet()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    var category : Category? = null

    @BatchSize(size = 100)
    @OrderBy("idx asc")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    var files: MutableSet<File> = LinkedHashSet()

    fun addFile(file: File) {
        files.add(file)
        file.product = this
    }
}
