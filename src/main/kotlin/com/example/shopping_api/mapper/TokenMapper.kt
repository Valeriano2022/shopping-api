package com.example.shopping_api.mapper

import com.example.shopping_api.dto.auth.TokenResponse


object TokenMapper {
    fun toResponse(accessToken: String): TokenResponse =
        TokenResponse(
            accessToken = accessToken
        )
}