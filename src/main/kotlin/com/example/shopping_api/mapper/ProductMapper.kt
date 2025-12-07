package com.example.shopping_api.mapper

import com.example.shopping_api.dto.product.ProductResponse
import com.example.shopping_api.model.Product
import org.springframework.stereotype.Component

@Component
object ProductMapper {
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
