package com.example.shopping_api.repository

import com.example.shopping_api.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository: JpaRepository<CartItem, Long>{
    fun findByCartId(cartId: Long): List<CartItem>
    fun findByCartIdAndProductId(cartId: Long, productId: Long): CartItem?
    fun deleteAllByCartId(cartId: Long)
}