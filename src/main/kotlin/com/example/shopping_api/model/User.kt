package com.example.shopping_api.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @Column(unique = true)
    val email: String,

    @Column(name = "password_hash")
    val passwordHash: String
) : BaseEntity()
