package com.example.shopping_api.controller

import com.example.shopping_api.dto.order.CheckOutRequest
import com.example.shopping_api.dto.order.OrderResponse
import com.example.shopping_api.hateoas.OrderLinks
import com.example.shopping_api.security.CustomUserPrincipal
import com.example.shopping_api.service.OrderService
import com.example.shopping_api.util.cast
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderControllerImpl(
    private val orderService: OrderService,
    private val orderLinks: OrderLinks,
    private val pagedAssembler: PagedResourcesAssembler<OrderResponse>
) : OrderController {

    @PostMapping
    override fun createOrder(
        auth: Authentication?,
        @RequestBody request: CheckOutRequest
    ): ResponseEntity<EntityModel<OrderResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val order = orderService.createOrder(principal.userId, request)
        val resource = orderLinks.addTo(EntityModel.of(order), order.id)
        return ResponseEntity.created(
            linkTo(methodOn(OrderController::class.java).getOrder(null, order.id)).toUri()
        ).body(resource)
    }

    @GetMapping("/{orderId}")
    override fun getOrder(
        auth: Authentication?,
        @PathVariable orderId: Long
    ): ResponseEntity<EntityModel<OrderResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val order = orderService.getOrder(principal.userId, orderId)
        val resource = orderLinks.addTo(EntityModel.of(order), orderId)
        return ResponseEntity.ok(resource)
    }

    @GetMapping
    override fun getOrders(
        auth: Authentication?,
        pageable: Pageable
    ): ResponseEntity<PagedModel<EntityModel<OrderResponse>>> {
        val principal = auth?.principal as CustomUserPrincipal
        val page = orderService.getOrders(principal.userId, pageable)
        val model = pagedAssembler.toModel(page)
        { dto -> orderLinks.addTo(EntityModel.of(dto), dto.id) }
            .cast<OrderResponse>()
        return ResponseEntity.ok(model)
    }

    @PatchMapping("/{orderId}/cancel")
    override fun cancelOrder(
        auth: Authentication?,
        @PathVariable orderId: Long
    ): ResponseEntity<EntityModel<OrderResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val order = orderService.cancelOrder(principal.userId, orderId)
        val resource = orderLinks.addTo(EntityModel.of(order), orderId)
        return ResponseEntity.ok(resource)
    }
}
