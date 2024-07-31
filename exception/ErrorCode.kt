package com.bubaum.pairing_server.exception

public enum class ErrorCode(var status: Int, var errorCode: String, var message: String) {
    FORBIDDEN(403, "FORBIDDEN-ERR-403", "권한이 없습니다"),
    UNAUTHORIZED(401, "UNAUTHORIZED-ERR-401", "자격 증명에 실패하였습니다."),
    ENTITY_NOT_FOUND(404, "ENTITY-NOT-FOUND-ERR-404", "존재하지 않는 데이터입니다."),
    BAD_REQUEST(400, "BAD_REQUEST-400", "잘못된 요청입니다"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL-SERVER-ERROR-500", "예상치 못한 오류가 발생하였습니다."),

    //중복된 아이디
    DUPLICATE_ID(400, "DUPLICATE-ID-400", "중복된 아이디입니다."),
    //유효한 리프래쉬 토큰이 아닐떄
    INVALID_REFRESH_TOKEN(400, "INVALID-REFRESH-TOKEN-400", "유효하지 않은 리프래쉬 토큰입니다."),
    //로그아웃된 사용자입니다
    LOGOUT_USER(400, "LOGOUT-USER-400", "로그아웃된 사용자입니다."),
    //토큰의 유저정보가 일치하지 않음
    NOT_MATCH_USER(400, "NOT-MATCH-USER-400", "토큰의 유저정보가 일치하지 않습니다."),
    //비밀번호 불일치
    NOT_MATCH_PASSWORD(400, "NOT-MATCH-PASSWORD-400", "비밀번호가 일치하지 않습니다."),
    //중복된 아이디
    DUPLICATE_USER_ID(400, "DUPLICATE-USER-ID-400", "중복된 아이디입니다."),
    //해당 유저가 없음
    NOT_FOUND_USER(400, "NOT-FOUND-USER-400", "해당 유저가 없습니다."),
    //잘못된 주소
    INVALID_ADDRESS(400, "INVALID-ADDRESS-400", "잘못된 주소입니다."),
    //수정 불가능한 상태
    NOT_MODIFIABLE_STATUS(400, "NOT-MODIFIABLE-STATUS-400", "수정 불가능한 상태입니다."),
    //삭제 불가능한 상태
    NOT_DELETE_STATUS(400, "NOT-DELETE-STATUS-400", "삭제 불가능한 상태입니다."),
    //타임아웃
    TIMEOUT(400, "TIMEOUT-400", "유효시간이 지났습니다."),
    //유효횟수 초과
    EXCEED_VALID_COUNT(400, "EXCEED-VALID-COUNT-400", "유효횟수를 초과하였습니다."),

    POINT_NOT_ENOUGH(400, "POINT-NOT-ENOUGH-400", "포인트가 부족합니다."),

    //세금계산서 발행 실패
    TAX_INVOICE_FAIL(400, "TAX-INVOICE-FAIL-400", "세금계산서 발행에 실패하였습니다."),

    //주문정보가 없음
    NOT_FOUND_ORDER(400, "NOT-FOUND-ORDER-400", "주문정보가 없습니다."),

    //회원정보가 일치하는 주문정보 없음
    NOT_MATCH_ORDER(400, "NOT-MATCH-ORDER-400", "회원정보가 일치하는 주문정보가 없습니다."),

    //만료된 쿠폰
    EXPIRED_COUPON(400, "EXPIRED-COUPON-400", "만료된 쿠폰입니다."),

    //포인트 부족
    NOT_ENOUGH_POINT(400, "NOT-ENOUGH-POINT-400", "포인트가 부족합니다."),

    //주문 금액이 일치하지 않음
    NOT_MATCH_ORDER_PRICE(400, "NOT-MATCH-ORDER-PRICE-400", "주문 금액이 일치하지 않습니다."),

    //결제 처리중 오류 발생
    PAYMENT_ERROR(400, "PAYMENT-ERROR-400", "결제 처리중 오류가 발생하였습니다."),

}
