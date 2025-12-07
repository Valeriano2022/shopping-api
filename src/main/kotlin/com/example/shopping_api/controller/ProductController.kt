package com.example.shopping_api.controller

import com.example.shopping_api.dto.product.ProductResponse
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity

interface ProductController {
    fun getProducts(pageable: Pageable): ResponseEntity<PagedModel<EntityModel<ProductResponse>>>
    fun getProduct(productId: Long): ResponseEntity<EntityModel<ProductResponse>>
}