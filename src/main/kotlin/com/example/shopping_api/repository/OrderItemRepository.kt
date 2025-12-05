package com.example.shopping_api.repository

import com.example.shopping_api.model.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long>