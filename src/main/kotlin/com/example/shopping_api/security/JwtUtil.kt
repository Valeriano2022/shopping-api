package com.example.shopping_api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @param: Value("\${jwt.secret}") private val secret: String,
    @param: Value("\${jwt.accessExpiration}") private val accessExpiration: Long,
    @param: Value("\${jwt.refreshExpiration}") private val refreshExpiration: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun generateAccessToken(userId: Long, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + accessExpiration)

        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }


    fun generateRefreshToken(userId: Long, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + refreshExpiration)
        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject

    fun isValid(token: String, email: String): Boolean =
        extractEmail(token) == email && !isExpired(token)

    fun parseUserId(token: String): Long {
        val claims = parseClaims(token)

        val raw = claims["userId"]
            ?: throw IllegalArgumentException("Missing userId claim in JWT")

        return when (raw) {
            is Int -> raw.toLong()
            is Long -> raw
            is Double -> raw.toLong()
            is String -> raw.toLongOrNull()
                ?: throw IllegalArgumentException("userId claim is not a valid number")
            else -> throw IllegalArgumentException("Invalid userId type in JWT: ${raw::class}")
        }
    }


     fun isExpired(token: String): Boolean =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())


     fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
