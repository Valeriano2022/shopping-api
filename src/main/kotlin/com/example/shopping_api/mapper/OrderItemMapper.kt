package com.example.shopping_api.mapper

import com.example.shopping_api.dto.order.OrderItemResponse
import com.example.shopping_api.model.OrderItem
import org.springframework.stereotype.Component

@Component
object OrderItemMapper {
    fun toResponse(orderItem: OrderItem) = OrderItemResponse(
        id = orderItem.id,
        product = ProductMapper.toResponse(orderItem.product),
        quantity = orderItem.quantity,
        priceAtPurchase = orderItem.priceAtPurchase,
        subtotal = orderItem.subtotal,
        createdAt = orderItem.createdAt.toString(),
        updatedAt = orderItem.updatedAt.toString()
    )
}
