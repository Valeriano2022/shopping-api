package com.example.shopping_api.mapper

import com.example.shopping_api.dto.auth.TokenResponse
import org.springframework.stereotype.Component

@Component
object TokenMapper {
    fun toResponse(accessToken: String): TokenResponse =
        TokenResponse(
            accessToken = accessToken
        )
}