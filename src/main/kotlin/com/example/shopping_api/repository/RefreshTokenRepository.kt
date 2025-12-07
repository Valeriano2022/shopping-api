package com.example.shopping_api.repository

import com.example.shopping_api.model.RefreshToken
import com.example.shopping_api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?
    fun findAllByUserAndExpiredIsFalseAndRevokedIsFalse(user: User): List<RefreshToken>
    fun existsByToken(token: String): Boolean?
}