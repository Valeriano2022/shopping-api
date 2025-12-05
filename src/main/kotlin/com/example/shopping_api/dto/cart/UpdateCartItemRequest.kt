package com.example.shopping_api.dto.cart

data class UpdateCartItemRequest(
    val cartItemId: Long,
    val quantity: Int
)
