package com.bubaum.pairing_server.enums

enum class PointDealReason(private val description: String) {

    PRODUCT_BUYING("상품구매"),
    REVIEW("리뷰작성"),
    EVENT("이벤트 참여"),
    ADMIN("관리자 지급"),

    BUYING_CANCEL("상품 구매취소"),
    REFUND("상품 환불"),
    REVIEW_DELETE("리뷰삭제"),
    EVENT_CANCEL("이벤트 취소"),
    ADMIN_CANCEL("관리자 차감"),
    EXPIRE("만료");

}