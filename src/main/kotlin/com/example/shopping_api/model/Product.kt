package com.example.shopping_api.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    val description: String?,
    val price: Double,
    val stock: Int,

    @Column(name = "photo_url")
    val photoUrl: String?
) : BaseEntity()
