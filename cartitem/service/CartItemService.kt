package com.bubaum.pairing_server.cartitem.service

import com.bubaum.pairing_server.cartitem.domain.dto.CartItemRequestDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartItemResponseDto
import com.bubaum.pairing_server.cartitem.domain.dto.CartSearchDto
import com.bubaum.pairing_server.global.dto.PageDto
import com.bubaum.pairing_server.cartitem.domain.entity.CartItem
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.cartitem.domain.repository.CartItemRepository
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.productoptiongroup.service.ProductOptionGroupService
import com.bubaum.pairing_server.product.service.ProductService
import com.bubaum.pairing_server.user.service.UserService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartItemService(
    private val cartItemRepository: CartItemRepository,
    private val userService: UserService,
    private val productService: ProductService,
    private val productOptionGroupService: ProductOptionGroupService
) {

    @Transactional
    fun create(cartItemRequestDto: CartItemRequestDto, userIdx: Long) : List<Long>{
        val user = userService.getUser(userIdx)
        val product = productService.getProduct(cartItemRequestDto.productIdx)
        val resultIdxList = mutableListOf<Long>()
        for(cartItem in cartItemRequestDto.cartItems){
            // 장바구니에 동일한 상품이 존재하면 수량만 증가
//            if (checkDtoList.isNotEmpty()) {
//                val cartItemDupCheck = checkForDuplicateCartItems(cartItem, checkDtoList)
//                if (cartItemDupCheck.isDuplicate) {
//                    cartItemRepository.cartItemCountPlus(cartItem.count, cartItemDupCheck.cartIdx!!)
//                    resultIdxList.add(cartItemDupCheck.cartIdx)
//                    continue
//                }
//            }

            cartItem.user = user
            cartItem.product = product
            cartItem.productOptionGroup = productOptionGroupService.getProductOptionGroup(cartItem.productOptionGroupIdx)
            val createCart = cartItemRepository.save(cartItem.toEntity())
            resultIdxList.add(createCart.idx!!)

        }
        return resultIdxList
    }

    fun get(idxList: List<Long>) : List<CartItem>{
        return cartItemRepository.findByIdxIn(idxList)
    }

    fun getCart(idx: Long) : CartItem {
        return cartItemRepository.findById(idx).orElseThrow{
            BaseException(ErrorCode.ENTITY_NOT_FOUND)
        }
    }


    fun existsByCartItem(idxs: List<Long>, userIdx: Long) : Boolean{
        return cartItemRepository.existsByCartItem(userIdx, idxs).isNotEmpty()
    }

    fun list(userIdx: Long, pageDto: PageDto, cartSearchDto: CartSearchDto) : Page<CartItemResponseDto>{
        return cartItemRepository.getCartItemPage(pageDto.pageable(), userIdx, cartSearchDto)
    }

    fun listV2(userIdx: Long,  idxList: List<Long>) : List<CartItemResponseDto>?{
        return cartItemRepository.getCartItems(userIdx, idxList) ?: throw BaseException(ErrorCode.ENTITY_NOT_FOUND)

    }

    fun getCartItemTotalPrice(userIdx: Long, idx: Long, couponIdx : Long) : Int{
        return cartItemRepository.getCartItemPrice(userIdx, idx, couponIdx)
    }

    @Transactional
    fun deleteCartItems(idxs: List<Long>, userIdx: Long) {
        if(cartItemRepository.deleteCartItems(idxs, userIdx) != idxs.size)
            throw BaseException(ErrorCode.ENTITY_NOT_FOUND)
    }
}
