package com.example.shopping_api.service

import com.example.shopping_api.dto.auth.LoginRequest
import com.example.shopping_api.dto.auth.LoginResponse
import com.example.shopping_api.dto.auth.RegisterRequest
import com.example.shopping_api.dto.auth.RegisterResponse
import com.example.shopping_api.dto.auth.TokenResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface AuthService {
    fun register(request: RegisterRequest): RegisterResponse
    fun login(request: LoginRequest, response: HttpServletResponse): LoginResponse
    fun logout(request: HttpServletRequest, response: HttpServletResponse)
    fun refresh(request: HttpServletRequest, response: HttpServletResponse) : TokenResponse
}