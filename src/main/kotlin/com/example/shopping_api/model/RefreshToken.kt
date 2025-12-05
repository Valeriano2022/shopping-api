package com.example.shopping_api.model

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
data class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Version
    var version: Long? = null,

    @Column(unique = true)
    var token: String,

    var expired: Boolean = false,
    var revoked: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
): BaseEntity()
