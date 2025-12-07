package com.example.shopping_api.controller

import com.example.shopping_api.dto.auth.LoginRequest
import com.example.shopping_api.dto.auth.LoginResponse
import com.example.shopping_api.dto.auth.RegisterRequest
import com.example.shopping_api.dto.auth.RegisterResponse
import com.example.shopping_api.dto.auth.TokenResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

interface AuthController {
    fun registerUser(request: RegisterRequest): ResponseEntity<RegisterResponse>
    fun loginUser(request: LoginRequest, response: HttpServletResponse):  ResponseEntity<LoginResponse>
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Unit>
    fun refresh(request: HttpServletRequest, response: HttpServletResponse):  ResponseEntity<TokenResponse>
}