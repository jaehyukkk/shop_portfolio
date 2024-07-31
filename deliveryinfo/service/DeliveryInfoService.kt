package com.bubaum.pairing_server.deliveryinfo.service

import com.bubaum.pairing_server.deliveryinfo.domain.dto.DeliveryInfoRequestDto
import com.bubaum.pairing_server.deliveryinfo.domain.entity.DeliveryInfo
import com.bubaum.pairing_server.user.domain.entity.User
import com.bubaum.pairing_server.exception.CustomMessageRuntimeException
import com.bubaum.pairing_server.deliveryinfo.domain.repository.DeliveryInfoRepository
import com.bubaum.pairing_server.exception.BaseException
import com.bubaum.pairing_server.exception.ErrorCode
import com.bubaum.pairing_server.user.service.UserService
import com.bubaum.pairing_server.utils.enum.result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeliveryInfoService(
    private val deliveryInfoRepository: DeliveryInfoRepository,
    private val userService: UserService
) {

    fun create(deliveryInfoRequestDto: DeliveryInfoRequestDto, user : User) : DeliveryInfo {
        if (deliveryInfoRequestDto.isDefault == true) {
            deliveryInfoRepository.modifyAllIsDeletedFalse()
        }
        return deliveryInfoRepository.save(deliveryInfoRequestDto.toEntity(user))
    }

    fun get(deliveryInfoIdx: Long) : DeliveryInfo {
        return deliveryInfoRepository.findById(deliveryInfoIdx).orElseThrow(){
            throw BaseException(ErrorCode.ENTITY_NOT_FOUND)
        }
    }

    fun getUserDeliveryInfo(userIdx: Long) : List<DeliveryInfo>{
        return deliveryInfoRepository.findByUser(userService.getUser(userIdx))
    }

    fun modify(deliveryInfoIdx: Long, deliveryInfoRequestDto: DeliveryInfoRequestDto, user: User) : DeliveryInfo {
        val deliveryInfo = get(deliveryInfoIdx)
        if(deliveryInfo.user != user){
            throw BaseException(ErrorCode.FORBIDDEN)
        }
        val deliveryInfoEntity = deliveryInfoRequestDto.toEntity(user)
        deliveryInfoEntity.idx = deliveryInfoIdx
        return deliveryInfoRepository.save(deliveryInfoEntity)
    }

    @Transactional
    fun modifyDefault(deliveryInfoIdx: Long, user: User) {
        val modifyIsDefault = deliveryInfoRepository.modifyIsDefault(deliveryInfoIdx, user)
        val modifyIsDefaultFalse = deliveryInfoRepository.modifyIsDefaultFalse(deliveryInfoIdx, user)
        if(modifyIsDefault == 0 || modifyIsDefaultFalse == 0){
            throw BaseException(ErrorCode.FORBIDDEN)
        }
    }

    @Transactional
    fun delete(deliveryInfoIdx: Long, user: User) {
        val deliveryInfo = get(deliveryInfoIdx)
        if(deliveryInfo.user != user){
            throw BaseException(ErrorCode.FORBIDDEN)
        }
        deliveryInfoRepository.delete(deliveryInfo)
    }
}
