package com.bubaum.pairing_server.enums

enum class PointStandard(private val description: String) {
    SELLING("판매가 설정 비율"),
    BUYING("구매가 설정 비율"),
    FIXED("고정 포인트"),
}