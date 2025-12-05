package com.example.shopping_api.model

import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val totalAmount: Double,

    val status: String,

    @Column(name = "cancel_reason")
    val cancelReason: String? = null
) : BaseEntity()
