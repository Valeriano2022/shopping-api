package com.example.shopping_api.controller

import com.example.shopping_api.dto.order.CheckOutRequest
import com.example.shopping_api.dto.order.OrderResponse
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface OrderController {
    fun createOrder(auth: Authentication?, request: CheckOutRequest): ResponseEntity<EntityModel<OrderResponse>>
    fun getOrder(auth: Authentication?, orderId: Long): ResponseEntity<EntityModel<OrderResponse>>
    fun getOrders(auth: Authentication?, pageable: Pageable): ResponseEntity<PagedModel<EntityModel<OrderResponse>>>
    fun cancelOrder(auth: Authentication?, orderId: Long): ResponseEntity<EntityModel<OrderResponse>>
}