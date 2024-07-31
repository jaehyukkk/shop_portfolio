package com.bubaum.pairing_server.exception

class BaseException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
