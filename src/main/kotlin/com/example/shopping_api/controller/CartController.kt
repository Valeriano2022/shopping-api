package com.example.shopping_api.controller


import com.example.shopping_api.dto.cart.AddToCartRequest
import com.example.shopping_api.dto.cart.CartItemResponse
import com.example.shopping_api.dto.cart.CartResponse
import com.example.shopping_api.dto.cart.UpdateCartItemRequest
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface CartController {
    fun getCart(auth: Authentication?): ResponseEntity<EntityModel<CartResponse>>
    fun addToCart(auth: Authentication?, request: AddToCartRequest): ResponseEntity<EntityModel<CartResponse>>
    fun updateCartItem(auth: Authentication?, cartItemId: Long, request: UpdateCartItemRequest): ResponseEntity<EntityModel<CartItemResponse>>
    fun removeCartItem(auth: Authentication?, cartItemId: Long): ResponseEntity<Unit>
    fun removeByProduct(auth: Authentication?, productId: Long): ResponseEntity<Unit>
    fun clearCart(auth: Authentication?): ResponseEntity<Unit>
}