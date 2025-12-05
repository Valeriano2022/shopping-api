package com.example.shopping_api.dto.cart

import com.example.shopping_api.dto.product.ProductResponse

data class CartItemResponse(
    val id: Long,
    val product: ProductResponse,
    val quantity: Int,
    val subtotal: Double,
    val createdAt: String,
    val updatedAt: String
)
