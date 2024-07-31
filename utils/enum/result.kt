package com.bubaum.pairing_server.utils.enum


enum class result(val status:Int){
    OK(200),
    FAIL(401),
    FORBIDDEN(403),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500),
    NOT_FOUND(404),
    UNAUTHORIZED(401);

}