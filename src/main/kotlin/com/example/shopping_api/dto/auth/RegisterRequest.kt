package com.example.shopping_api.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field: NotBlank(message = "Name is required.")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    @field:Pattern(
        regexp = "^[A-Za-z0-9'.-]+( [A-Za-z0-9'.-]+)*$",
        message = "Name contains invalid characters. Only letters, numbers, spaces, and '.- are allowed."
    )
    var name: String,
    @field: Email(message = "Email must be valid.")
    @field:Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format")
    var email: String,
    @field: NotBlank
    var password: String
){
    init {
        email = email.trim().lowercase()
    }
}
