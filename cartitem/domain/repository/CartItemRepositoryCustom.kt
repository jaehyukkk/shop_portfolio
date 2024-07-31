package com.bubaum.pairing_server.cartitem.domain.repository

import com.bubaum.pairing_server.cartitem.domain.dto.CartItemResponseDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartSearchDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CartItemRepositoryCustom {

    fun getCartItemPage(pageable: Pageable, userIdx: Long, cartSearchDto: CartSearchDto) : Page<CartItemResponseDto>

    fun getCartItems(userIdx: Long, idxList: List<Long>) : List<CartItemResponseDto>?


    fun existsByCartItem(userIdx: Long, productOptionGroupIdxs: List<Long>) : List<Long>

}