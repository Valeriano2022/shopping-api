package com.example.shopping_api.repository

import com.example.shopping_api.model.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    fun findByUserId(userId: Long): Cart?
}