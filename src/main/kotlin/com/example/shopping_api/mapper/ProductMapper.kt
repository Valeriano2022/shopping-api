package com.example.shopping_api.mapper

import com.example.shopping_api.dto.product.ProductRequest
import com.example.shopping_api.dto.product.ProductResponse
import com.example.shopping_api.model.Product

object ProductMapper {
    fun toEntity(req: ProductRequest) = Product(
        name = req.name,
        description = req.description,
        price = req.price,
        stock = req.stock,
        photoUrl = req.photoUrl
    )

    fun toResponse(product: Product) = ProductResponse(
        id = product.id,
        name = product.name,
        description = product.description,
        price = product.price,
        stock = product.stock,
        photoUrl = product.photoUrl,
        createdAt = product.createdAt.toString(),
        updatedAt = product.updatedAt.toString()
    )
}
