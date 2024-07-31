package com.bubaum.pairing_server.enums

enum class PayType(val value: String) {
    CARD("카드"),
    MOBILE_PHONE("휴대폰"),
    EASY_PAY("간편결제"),
    VIRTUAL_ACCOUNT("가상계좌"),
    TRANSFER("계좌이체"),
    GIFT_CERTIFICATE("상품권"),
}