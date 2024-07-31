package com.bubaum.pairing_server.converter

import com.bubaum.pairing_server.enums.DeliveryCompany
import org.springframework.core.convert.converter.Converter

class DeliveryCompanyConverter : Converter<String, DeliveryCompany> {
    override fun convert(source: String): DeliveryCompany? {
        return DeliveryCompany.getByCode(source)
    }
}