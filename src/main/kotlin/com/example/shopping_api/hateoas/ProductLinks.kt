package com.example.shopping_api.hateoas

import com.example.shopping_api.controller.ProductController
import org.springframework.data.domain.PageRequest
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class ProductLinks {
    val dummyPageable = PageRequest.of(0, 10)
    fun self(productId: Long): Link =
        linkTo(methodOn(ProductController::class.java).getProduct(productId))
            .withSelfRel()

    fun list(): Link =
        linkTo(methodOn(ProductController::class.java).getProducts(dummyPageable))
            .withRel("products")

    fun <T : Any> addTo(model: EntityModel<T>, productId: Long): EntityModel<T> {
        model.add(self(productId))
        model.add(list())

        return model
    }
}
