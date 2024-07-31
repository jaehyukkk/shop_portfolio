package com.bubaum.pairing_server.global.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class ResultMsg(
    val status:Int,
    val msg:String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val result: Any?=null
)