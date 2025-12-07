package com.example.shopping_api.mapper

import com.example.shopping_api.dto.cart.CartItemResponse
import com.example.shopping_api.model.CartItem
import org.springframework.stereotype.Component

@Component
object CartItemMapper {
    fun toResponse(cartItem: CartItem) = CartItemResponse(
        id = cartItem.id,
        product = ProductMapper.toResponse(cartItem.product),
        quantity = cartItem.quantity,
        subtotal = cartItem.subtotal,
        createdAt = cartItem.createdAt.toString(),
        updatedAt = cartItem.updatedAt.toString()
    )
}