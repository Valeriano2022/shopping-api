package com.example.shopping_api.dto.cart

data class AddToCartRequest(
    val productId: Long,
    val quantity: Int
)
