package com.example.shopping_api.mapper

import com.example.shopping_api.dto.cart.CartResponse
import com.example.shopping_api.model.Cart
import com.example.shopping_api.model.CartItem

object CartMapper {
    fun toResponse(cart: Cart, items: List<CartItem>) = CartResponse(
        id = cart.id,
        userId = cart.user.id,
        items = items.map { CartItemMapper.toResponse(it) },
        totalAmount = items.sumOf { it.subtotal },
        updatedAt = cart.updatedAt.toString()
    )
}
