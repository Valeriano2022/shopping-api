package com.example.shopping_api.mapper

import com.example.shopping_api.dto.user.UserResponse
import com.example.shopping_api.model.User

object UserMapper {
    fun toResponse(user: User) = UserResponse(
        id = user.id,
        name = user.name,
        email = user.email,
        createdAt = user.createdAt.toString(),
        updatedAt = user.updatedAt.toString()
    )
}