package com.example.shopping_api.mapper

import com.example.shopping_api.dto.order.OrderResponse
import com.example.shopping_api.model.Order
import com.example.shopping_api.model.OrderItem

object OrderMapper {
    fun toResponse(order: Order, items: List<OrderItem>) = OrderResponse(
        id = order.id,
        userId = order.user.id,
        totalAmount = order.totalAmount,
        status = order.status,
        cancelReason = order.cancelReason,
        items = items.map { OrderItemMapper.toResponse(it) },
        createdAt = order.createdAt.toString(),
        updatedAt = order.updatedAt.toString()
    )
}
