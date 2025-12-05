package com.example.shopping_api.service

import com.example.shopping_api.dto.user.UserRequest
import com.example.shopping_api.dto.user.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun create(userId: Long, request: UserRequest) : UserResponse
    fun readAll(pageable: Pageable): Page<UserResponse>
    fun read(userId: Long): UserResponse
    fun update(userId: Long, request: UserRequest): UserResponse
    fun delete(userId: Long)
}