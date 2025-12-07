package com.example.shopping_api.repository

import com.example.shopping_api.model.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long>{
    fun findByOrderId(orderId: Long): List<OrderItem>
}