package com.example.shopping_api.dto.cart

data class CartResponse(
    val id: Long,
    val userId: Long,
    val items: List<CartItemResponse>,
    val totalAmount: Double,
    val updatedAt: String
)
