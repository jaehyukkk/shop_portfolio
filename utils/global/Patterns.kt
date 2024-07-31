package com.bubaum.pairing_server.utils.global

import org.springframework.stereotype.Component

@Component
class Patterns {
    companion object {
        const val ID = "^(?=.*[a-zA-Z])[a-zA-Z0-9_-]{6,}$"
        const val PASSWORD = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$\\-_,.<>])[a-zA-Z0-9!@#$\\-_,.<>]{8,}$"
        const val EMAIL = "^[a-zA-Z0-9._%+-\\.]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        const val PHONE_NUMBER = "^01[016789][0-9]{3,4}[0-9]{4}$"
        const val VERIFICATION_CODE = "^[0-9]{6}$"
        const val YYYYMMDD = "^(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"
        const val HHMMSS = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9][0-5][0-9]$"
        const val YYYYMMDDHHMMSS = "^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])([01][0-9]|2[0-3])([0-5][0-9]){2}$"

    }

}