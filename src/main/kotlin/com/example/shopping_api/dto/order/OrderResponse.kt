package com.example.shopping_api.dto.order

data class OrderResponse(
    val id: Long,
    val userId: Long,
    val totalAmount: Double,
    val status: String,
    val cancelReason: String?,
    val items: List<OrderItemResponse>,
    val createdAt: String,
    val updatedAt: String
)

