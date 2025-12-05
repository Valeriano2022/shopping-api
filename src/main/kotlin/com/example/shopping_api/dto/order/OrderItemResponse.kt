package com.example.shopping_api.dto.order

import com.example.shopping_api.dto.product.ProductResponse

data class OrderItemResponse(
    val id: Long,
    val product: ProductResponse,
    val quantity: Int,
    val priceAtPurchase: Double,
    val subtotal: Double,
    val createdAt: String,
    val updatedAt: String
)
