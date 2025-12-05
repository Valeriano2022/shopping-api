package com.example.shopping_api.dto.product

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val stock: Int,
    val photoUrl: String?,
    val createdAt: String,
    val updatedAt: String
)
