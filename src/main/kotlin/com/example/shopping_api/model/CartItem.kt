package com.example.shopping_api.model

import jakarta.persistence.*

@Entity
@Table(
    name = "cart_items",
    uniqueConstraints = [
        UniqueConstraint(name = "ux_cart_item_unique", columnNames = ["cart_id", "product_id"])
    ]
)
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    var cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product,

    var quantity: Int,
    var subtotal: Double
    ) : BaseEntity()
