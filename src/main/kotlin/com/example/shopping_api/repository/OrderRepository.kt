package com.example.shopping_api.repository

import com.example.shopping_api.model.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<Order>
    fun findByCreatedAtBeforeAndStatusNot(date: LocalDateTime, status: String): List<Order>
}