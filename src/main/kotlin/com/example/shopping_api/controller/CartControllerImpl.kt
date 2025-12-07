package com.example.shopping_api.controller

import com.example.shopping_api.dto.cart.AddToCartRequest
import com.example.shopping_api.dto.cart.CartItemResponse
import com.example.shopping_api.dto.cart.CartResponse
import com.example.shopping_api.dto.cart.UpdateCartItemRequest
import com.example.shopping_api.hateoas.CartLinks
import com.example.shopping_api.security.CustomUserPrincipal
import com.example.shopping_api.service.CartService
import jakarta.validation.Valid
import org.springframework.hateoas.EntityModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartControllerImpl(
    private val cartService: CartService,
    private val cartLinks: CartLinks
) : CartController {

    @GetMapping
    override fun getCart(auth: Authentication?): ResponseEntity<EntityModel<CartResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val cart = cartService.getCart(principal.userId)
        val resource = cartLinks.addToCartResource(
            model = EntityModel.of(cart),
            cartItemId = null,
            productId = null
        )
        return ResponseEntity.ok(resource)
    }

    @PostMapping("/items")
    override fun addToCart(
        auth: Authentication?,
        @Valid @RequestBody request: AddToCartRequest
    ): ResponseEntity<EntityModel<CartResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val updatedCart = cartService.add(principal.userId, request)
        val resource = cartLinks.addToCartResource(
            model = EntityModel.of(updatedCart),
            cartItemId = null,
            productId = request.productId
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(resource)
    }

    @PutMapping("/items/{id}")
    override fun updateCartItem(
        auth: Authentication?,
        @PathVariable("id") cartItemId: Long,
        @Valid @RequestBody request: UpdateCartItemRequest
    ): ResponseEntity<EntityModel<CartItemResponse>> {
        val principal = auth?.principal as CustomUserPrincipal
        val updatedItem = cartService.update(principal.userId, request.copy(cartItemId = cartItemId))
        val resource = cartLinks.addToCartResource(
            model = EntityModel.of(updatedItem),
            cartItemId = updatedItem.id,
            productId = updatedItem.product.id
        )
        return ResponseEntity.ok(resource)
    }

    @DeleteMapping("/items/{id}")
    override fun removeCartItem(
        auth: Authentication?,
        @PathVariable("id") cartItemId: Long
    ): ResponseEntity<Unit> {
        val principal = auth?.principal as CustomUserPrincipal
        cartService.remove(principal.userId, cartItemId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/products/{id}")
    override fun removeByProduct(
        auth: Authentication?,
        @PathVariable("id") productId: Long
    ): ResponseEntity<Unit> {
        val principal = auth?.principal as CustomUserPrincipal
        cartService.removeByProduct(principal.userId, productId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    override fun clearCart(auth: Authentication?): ResponseEntity<Unit> {
        val principal = auth?.principal as CustomUserPrincipal
        cartService.clear(principal.userId)
        return ResponseEntity.noContent().build()
    }
}
