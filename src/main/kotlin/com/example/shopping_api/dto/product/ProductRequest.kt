package com.example.shopping_api.dto.product

data class ProductRequest(
    val name: String,
    val description: String?,
    val price: Double,
    val stock: Int,
    val photoUrl: String?
)
