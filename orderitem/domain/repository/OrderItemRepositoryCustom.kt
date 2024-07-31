package com.bubaum.pairing_server.orderitem.domain.repository

import com.bubaum.pairing_server.orderitem.domain.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderItemRepositoryCustom {
//    fun getOrderItemList(userIdx: Long) : List<OrderListResponseDto>

    fun getDashboardOrderItems(pageable: Pageable, dashboardOrderItemSearchDto: DashboardOrderItemSearchDto) : Page<DashboardOrderItemListResponseDto>
    fun getUserOrderItems(pageable: Pageable, userIdx : Long) : Page<UserOrderItemListResponseDto>

    fun getDashboardDeliveryManagementList(pageable: Pageable, dashboardDeliveryManagementSearchDto: DashboardDeliveryManagementSearchDto) : Page<DashboardDeliveryManagementListDto>
}


