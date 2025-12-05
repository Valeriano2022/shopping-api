package com.example.shopping_api.service

import com.example.shopping_api.dto.order.CheckOutRequest
import com.example.shopping_api.dto.order.OrderResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderService {
    fun createOrder(userId: Long, request: CheckOutRequest): OrderResponse
    fun getOrders(userId: Long, pageable: Pageable): Page<OrderResponse>
    fun getOrder(userId: Long, orderId: Long): OrderResponse
    fun cancelOrder(userId: Long, orderId: Long): OrderResponse
}