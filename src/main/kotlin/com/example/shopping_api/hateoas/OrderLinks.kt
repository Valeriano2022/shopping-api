package com.example.shopping_api.hateoas

import com.example.shopping_api.controller.OrderController
import com.example.shopping_api.dto.order.CheckOutRequest
import org.springframework.data.domain.PageRequest
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class OrderLinks {
    val dummyRequest = CheckOutRequest(paymentMethod = "DUMMY")
    val dummyPageable = PageRequest.of(0, 10)

    fun self(orderId: Long): Link =
        linkTo(methodOn(OrderController::class.java).getOrder(null, orderId))
            .withSelfRel()

    fun cancel(orderId: Long): Link =
        linkTo(methodOn(OrderController::class.java).cancelOrder(null, orderId))
            .withRel("cancel")

    fun list(): Link =
        linkTo(methodOn(OrderController::class.java).getOrders(null, dummyPageable))
            .withRel("orders")

    fun create(): Link =
        linkTo(methodOn(OrderController::class.java).createOrder(null, dummyRequest))
            .withRel("create")

    fun <T : Any> addTo(model: EntityModel<T>, orderId: Long): EntityModel<T> =
        model
            .add(self(orderId))
            .add(cancel(orderId))
            .add(list())
            .add(create())
}
