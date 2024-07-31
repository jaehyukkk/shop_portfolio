package com.bubaum.pairing_server.review.domain.dto

interface ReviewPercentageResponseDto {
        fun getRating() : Int
        fun getPercentage(): Double
}