package com.bubaum.pairing_server.enums

enum class CreditCardCompany(val code: String, val koreanName: String, val englishName: String) {
    IBK_BC("3K", "기업비씨", "IBK_BC"),
    GWANGJUBANK("46", "광주", "GWANGJUBANK"),
    LOTTE("71", "롯데", "LOTTE"),
    KDBBANK("30", "산업", "KDBBANK"),
    BC("31", "-", "BC"),
    SAMSUNG("51", "삼성", "SAMSUNG"),
    SAEMAUL("38", "새마을", "SAEMAUL"),
    SHINHAN("41", "신한", "SHINHAN"),
    SHINHYEOP("62", "신협", "SHINHYEOP"),
    CITI("36", "씨티", "CITI"),
    WOORI("33", "우리", "WOORI"),
    W1("W1", "우리", "WOORI"),
    POST("37", "우체국", "POST"),
    SAVINGBANK("39", "저축", "SAVINGBANK"),
    JEONBUKBANK("35", "전북", "JEONBUKBANK"),
    JEJUBANK("42", "제주", "JEJUBANK"),
    KAKAOBANK("15", "카카오뱅크", "KAKAOBANK"),
    KBANK("3A", "케이뱅크", "KBANK"),
    TOSSBANK("24", "토스뱅크", "TOSSBANK"),
    HANA("21", "하나", "HANA"),
    HYUNDAI("61", "현대", "HYUNDAI"),
    KOOKMIN("11", "국민", "KOOKMIN"),
    NONGHYEOP("91", "농협", "NONGHYEOP"),
    SUHYEOP("34", "수협", "SUHYEOP")
}