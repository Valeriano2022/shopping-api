package com.example.shopping_api.controller

import com.example.shopping_api.dto.product.ProductResponse
import com.example.shopping_api.hateoas.ProductLinks
import com.example.shopping_api.service.ProductService
import com.example.shopping_api.util.cast
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductControllerImpl(
    private val productService: ProductService,
    private val productLinks: ProductLinks,
    private val pagedAssembler: PagedResourcesAssembler<ProductResponse>
) : ProductController {

    @GetMapping
    override fun getProducts(pageable: Pageable): ResponseEntity<PagedModel<EntityModel<ProductResponse>>> {
        val page = productService.getProducts(pageable)

        val model = pagedAssembler.toModel(page)
        { dto -> productLinks.addTo(EntityModel.of(dto), dto.id) }
            .cast<ProductResponse>()

        return ResponseEntity.ok(model)
    }

    @GetMapping("/{productId}")
    override fun getProduct(@PathVariable productId: Long): ResponseEntity<EntityModel<ProductResponse>> {
        val product = productService.getProduct(productId)
        val resource = productLinks.addTo(EntityModel.of(product), productId)
        return ResponseEntity.ok(resource)
    }
}
