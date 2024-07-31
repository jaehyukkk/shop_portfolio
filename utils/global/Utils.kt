package com.bubaum.pairing_server.utils.global

import com.bubaum.pairing_server.enums.CreditCardCompany

object Utils {
    fun getCreditCard(code: String): CreditCardCompany? {
        return CreditCardCompany.values().find { it.code == code }
    }

}