package com.bubaum.pairing_server.cartitem.controller

import com.bubaum.pairing_server.cartitem.domain.dto.CartItemRequestDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartItemResponseDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartSearchDto
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.cartitem.service.CartItemService
import com.bubaum.pairing_server.global.dto.ResultMsg
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/cart-item")
class CartItemController(
    private val cartItemService: CartItemService
) {

    @PostMapping()
    fun create(@RequestBody cartItemRequestDto: CartItemRequestDto, principal: Principal): ResponseEntity<List<Long>> {
        return ResponseEntity.ok(cartItemService.create(cartItemRequestDto, principal.name.toLong()))
    }

    @GetMapping("/exists/{idxs}")
    fun existsByCartItem(
        @PathVariable idxs: List<Long>,
        principal: Principal
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(
            cartItemService.existsByCartItem(idxs, principal.name.toLong()
        ))
    }

    @GetMapping()
    fun list(pageDto: PageDto, principal: Principal, cartSearchDto: CartSearchDto): ResponseEntity<Page<CartItemResponseDto>> {
        return ResponseEntity.ok(
            cartItemService.list(principal.name.toLong(), pageDto, cartSearchDto)
        )
    }

    @GetMapping("/{idxList}")
    fun getCartItems(@PathVariable idxList: List<Long>, principal: Principal): ResponseEntity<List<CartItemResponseDto>> {
        return ResponseEntity.ok(
            cartItemService.listV2(principal.name.toLong(), idxList)
        )
    }

    @DeleteMapping("/{idxs}")
    fun deleteCartItems(@PathVariable idxs: List<Long>, principal: Principal) : ResponseEntity<Void> {
        cartItemService.deleteCartItems(idxs, principal.name.toLong())
        return ResponseEntity.ok().build()
    }
}
