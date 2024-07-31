package com.bubaum.pairing_server.cartitem.domain.repository

import com.bubaum.pairing_server.cartitem.domain.entity.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CartItemRepository : JpaRepository<CartItem, Long>, CartItemRepositoryCustom {


    fun findByIdxIn(idxList: List<Long>): List<CartItem>

    @Query(
        value = "SELECT\n" +
                "    IF(coupon.coupon_type = 0, ((b.price + SUM(d.add_price)) * a.count) * (1 - coupon.discount / 100),\n" +
                "       IF(coupon.coupon_type IS NULL, ((b.price + SUM(d.add_price)) * a.count),((b.price + SUM(d.add_price)) * a.count) - coupon.discount)) AS total_price\n" +
                "FROM cart_item a\n" +
                "         LEFT JOIN (\n" +
                "    SELECT\n" +
                "        bb.discount\n" +
                "         , aa.member_idx\n" +
                "         , bb.coupon_type\n" +
                "    FROM publish_coupon aa\n" +
                "             LEFT JOIN coupon bb ON aa.coupon_idx = bb.idx\n" +
                "    WHERE aa.member_idx = :userIdx\n" +
                "      AND aa.idx = :couponIdx\n" +
                "      AND bb.expired_at > now()\n" +
                "      AND aa.is_used = 0\n" +
                ") coupon ON a.user_id = coupon.member_idx\n" +
                "         LEFT JOIN product b ON a.product_idx = b.idx\n" +
                "    LEFT JOIN product_option_group d ON a.product_option_group_idx = d.idx\n" +
                "         LEFT JOIN user e ON a.user_id = e.idx\n" +
                "WHERE a.idx = :cartItemIdx AND e.idx = :userIdx\n" +
                "GROUP BY a.idx, b.price, coupon.discount, coupon.coupon_type", nativeQuery = true
    )
    fun getCartItemPrice(userIdx : Long, cartItemIdx : Long, couponIdx : Long) : Int
    @Transactional
    @Modifying
    @Query("update CartItem a set a.count = a.count + :count where a.idx = :idx")
    fun cartItemCountPlus(count: Int, idx: Long) : Int

    @Transactional
    @Modifying
    @Query("update CartItem a set a.isDeleted = true where a.idx in :idxs and a.user.idx = :userIdx")
    fun deleteCartItems(idxs: List<Long>, userIdx: Long) : Int

}