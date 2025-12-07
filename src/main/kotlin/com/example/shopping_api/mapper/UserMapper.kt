package com.example.shopping_api.mapper

import com.example.shopping_api.dto.auth.LoginResponse
import com.example.shopping_api.dto.auth.RegisterRequest
import com.example.shopping_api.dto.auth.RegisterResponse
import com.example.shopping_api.dto.user.UserResponse
import com.example.shopping_api.model.User
import org.springframework.stereotype.Component

@Component
object UserMapper {

    fun toResponse(user: User) = RegisterResponse(
        user = UserResponse(
            id = user.id,
            email = user.email,
            name = user.name,
        ),
    )

    fun RegisterRequest.toEntity() = User(
        name = this.name,
        email = this.email,
        password = this.password
    )

    fun toResponse(accessToken: String, user: User) = LoginResponse(
        accessToken = accessToken,
        user = UserResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
    )
}