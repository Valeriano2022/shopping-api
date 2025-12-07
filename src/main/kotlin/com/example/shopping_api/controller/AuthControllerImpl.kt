package com.example.shopping_api.controller

import com.example.shopping_api.dto.auth.LoginRequest
import com.example.shopping_api.dto.auth.LoginResponse
import com.example.shopping_api.dto.auth.RegisterRequest
import com.example.shopping_api.dto.auth.RegisterResponse
import com.example.shopping_api.dto.auth.TokenResponse
import com.example.shopping_api.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthControllerImpl(
    private val authService: AuthService,
) : AuthController {

    @PostMapping("/register")
    override fun registerUser(@Valid @RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(RegisterResponse("Registered successfully!"))
    }
    @PostMapping("/login")
    override fun loginUser(
        @Valid @RequestBody request: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<LoginResponse> {
        val auth = authService.login(request,response)
        return ResponseEntity.status(HttpStatus.OK).body(auth)
    }
    @PostMapping("/logout")
    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        authService.logout(request,response)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
    @PostMapping("/refresh")
    override fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<TokenResponse> {
        val token = authService.refresh(request,response)
        return ResponseEntity.status(HttpStatus.OK).body(token)
    }
}
