package com.example.shopping_api.service

import com.example.shopping_api.dto.product.ProductResponse
import com.example.shopping_api.exception.ProductNotFoundException
import com.example.shopping_api.mapper.ProductMapper
import com.example.shopping_api.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) : ProductService {

    override fun getProduct(productId: Long): ProductResponse {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException() }

        return productMapper.toResponse(product)
    }

    override fun getProducts(pageable: Pageable): Page<ProductResponse> {
        val products = productRepository.findAll(pageable)

        return products.map { product ->
            productMapper.toResponse(product)
        }
    }
}
