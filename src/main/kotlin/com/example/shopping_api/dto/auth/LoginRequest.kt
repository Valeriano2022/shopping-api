package com.example.shopping_api.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class LoginRequest(
    @field: Email(message = "Email must be valid.")
    @field:Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format")
    var email: String,
    @field: NotBlank
    var password: String
) {
    init {
        email = email.trim()
        password = password.trim()
    }
}
