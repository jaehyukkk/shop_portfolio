package com.bubaum.pairing_server.exception

import com.bubaum.pairing_server.utils.enum.result
import java.lang.RuntimeException

class CustomMessageRuntimeException (
    val errorCode: ErrorCode,
    val msg: String
):RuntimeException(msg){
}
