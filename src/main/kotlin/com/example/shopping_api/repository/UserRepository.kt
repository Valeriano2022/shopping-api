package com.example.shopping_api.repository

import com.example.shopping_api.model.User
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import java.awt.print.Pageable

interface UserRepository : JpaRepository<User, Long>{
    fun findAll(pageable: Pageable): Page<User>
    fun findByEmail(email: String): User?
}