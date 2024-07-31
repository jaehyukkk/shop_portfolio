package com.bubaum.pairing_server.utils.global

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken


object GsonUtil {
    private const val PATTERN_DATE = "yyyy-MM-dd"
    private const val PATTERN_TIME = "HH:mm:ss"
    private val PATTERN_DATETIME = String.format("%s %s", PATTERN_DATE, PATTERN_TIME)
    private val gson: Gson = Gson()
    fun toJson(o: Any?): String? {
        val result: String = gson.toJson(o)
        return if ("string" == result) null else result
    }

    fun <T> fromJson(s: String?, typeToken: TypeToken<T>): T? {
        try {
            return gson.fromJson(s, typeToken.type)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
        return null
    }
}