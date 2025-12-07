package com.example.shopping_api.hateoas

import com.example.shopping_api.controller.CartController
import com.example.shopping_api.dto.cart.AddToCartRequest
import com.example.shopping_api.dto.cart.UpdateCartItemRequest
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class CartLinks {
    val dummyRequest = AddToCartRequest(
        productId = 0,
        quantity = 0
    )
    val dummyUpdateRequest = UpdateCartItemRequest(
        cartItemId = 0,
        quantity = 0
    )
    fun self(): Link =
        linkTo(methodOn(CartController::class.java).getCart(null))
            .withSelfRel()

    fun addItem(): Link =
        linkTo(methodOn(CartController::class.java).addToCart(null, dummyRequest))
            .withRel("add")

    fun clear(): Link =
        linkTo(methodOn(CartController::class.java).clearCart(null))
            .withRel("clear")

    fun removeItem(cartItemId: Long): Link =
        linkTo(methodOn(CartController::class.java).removeCartItem(null, cartItemId))
            .withRel("remove-item")

    fun updateItem(cartItemId: Long): Link =
        linkTo(methodOn(CartController::class.java).updateCartItem(null, cartItemId, dummyUpdateRequest))
            .withRel("update-item")

    fun removeByProduct(productId: Long): Link =
        linkTo(methodOn(CartController::class.java).removeByProduct(null, productId))
            .withRel("remove-by-product")

    fun <T : Any> addToCartResource(model: EntityModel<T>, cartItemId: Long?, productId: Long?): EntityModel<T> {
        model.add(self())
        model.add(addItem())
        model.add(clear())

        cartItemId?.let {
            model.add(updateItem(it))
            model.add(removeItem(it))
        }

        productId?.let {
            model.add(removeByProduct(it))
        }

        return model
    }
}
