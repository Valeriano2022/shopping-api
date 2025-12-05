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

    var status: String,

    @Column(name = "cancel_reason")
    var cancelReason: String? = null
) : BaseEntity()
