package com.example.shopping_api.service

import com.example.shopping_api.dto.order.CheckOutRequest
import com.example.shopping_api.dto.order.OrderResponse
import com.example.shopping_api.exception.CartNotFoundException
import com.example.shopping_api.exception.EmptyCartException
import com.example.shopping_api.exception.OrderAlreadyCancelledException
import com.example.shopping_api.exception.OrderCancellationNotAllowedException
import com.example.shopping_api.exception.OrderNotFoundException
import com.example.shopping_api.exception.UnauthorizedOperationException
import com.example.shopping_api.exception.UserNotFoundException
import com.example.shopping_api.mapper.OrderMapper
import com.example.shopping_api.model.Order
import com.example.shopping_api.model.OrderItem
import com.example.shopping_api.repository.CartItemRepository
import com.example.shopping_api.repository.CartRepository
import com.example.shopping_api.repository.OrderItemRepository
import com.example.shopping_api.repository.OrderRepository
import com.example.shopping_api.repository.ProductRepository
import com.example.shopping_api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val userRepository: UserRepository,
    private val orderMapper: OrderMapper
): OrderService {
    override fun createOrder(userId: Long, request: CheckOutRequest): OrderResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }

        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val cartItems = cartItemRepository.findByCartId(cart.id)

        if (cartItems.isEmpty())
            throw EmptyCartException()

        val totalAmount = cartItems.sumOf { it.subtotal }

        val order = orderRepository.save(
            Order(
                user = user,
                totalAmount = totalAmount,
                status = "NEW",
                cancelReason = null
            )
        )

        val orderItems = cartItems.map {
            OrderItem(
                order = order,
                product = it.product,
                quantity = it.quantity,
                priceAtPurchase = it.product.price,
                subtotal = it.subtotal
            )
        }
        orderItemRepository.saveAll(orderItems)
        cartItemRepository.deleteAll(cartItems)

        return orderMapper.toResponse(order, orderItems)
    }


    override fun getOrders(userId: Long, pageable: Pageable): Page<OrderResponse> {
        val orders = orderRepository.findByUserId(userId, pageable)

        return orders.map { order ->
            val items = orderItemRepository.findByOrderId(order.id)
            OrderMapper.toResponse(order, items)
        }
    }

    override fun getOrder(userId: Long, orderId: Long): OrderResponse {
        val order = orderRepository.findById(orderId)
            .orElseThrow { OrderNotFoundException() }

        if (order.user.id != userId)
            throw UnauthorizedOperationException("Forbidden")

        val items = orderItemRepository.findByOrderId(orderId)

        return OrderMapper.toResponse(order, items)
    }

    override fun cancelOrder(userId: Long, orderId: Long): OrderResponse {
        val order = orderRepository.findById(orderId)
            .orElseThrow { OrderNotFoundException() }

        if (order.user.id != userId)
            throw UnauthorizedOperationException("Forbidden")

        if (order.status == "CANCELLED")
            throw OrderAlreadyCancelledException()

        val sevenDaysAgo = LocalDateTime.now().minusDays(7)

        if (order.createdAt.isBefore(sevenDaysAgo).not())
            throw OrderCancellationNotAllowedException()

        order.status = "CANCELLED"
        order.cancelReason = "Cancelled by user"

        val saved = orderRepository.save(order)
        val items = orderItemRepository.findByOrderId(order.id)

        return OrderMapper.toResponse(saved, items)
    }
}