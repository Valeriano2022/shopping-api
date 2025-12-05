package com.example.shopping_api.dto.auth

import com.example.shopping_api.dto.user.UserResponse

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)
