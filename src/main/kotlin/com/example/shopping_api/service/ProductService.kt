package com.example.shopping_api.service

import com.example.shopping_api.dto.product.ProductResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun getProduct(productId: Long): ProductResponse
    fun getProducts(pageable: Pageable): Page<ProductResponse>
}