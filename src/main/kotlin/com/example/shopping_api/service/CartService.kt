package com.example.shopping_api.service

import com.example.shopping_api.dto.cart.AddToCartRequest
import com.example.shopping_api.dto.cart.CartItemResponse
import com.example.shopping_api.dto.cart.CartResponse
import com.example.shopping_api.dto.cart.UpdateCartItemRequest

interface CartService {
    fun getCart(userId: Long): CartResponse
    fun add(userId: Long, request: AddToCartRequest): CartResponse
    fun update(userId: Long, request: UpdateCartItemRequest?): CartItemResponse
    fun remove(userId: Long, cartItemId: Long)
    fun removeByProduct(userId: Long, productId: Long)
    fun clear(userId: Long)
}