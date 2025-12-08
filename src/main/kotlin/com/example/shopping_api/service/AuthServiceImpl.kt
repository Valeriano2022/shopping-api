package com.example.shopping_api.service

import com.example.shopping_api.dto.auth.LoginRequest
import com.example.shopping_api.dto.auth.LoginResponse
import com.example.shopping_api.dto.auth.RegisterRequest
import com.example.shopping_api.dto.auth.RegisterResponse
import com.example.shopping_api.dto.auth.TokenResponse
import com.example.shopping_api.exception.EmailAlreadyExistsException
import com.example.shopping_api.exception.InvalidRequestException
import com.example.shopping_api.exception.TokenExpiredException
import com.example.shopping_api.exception.TokenNotFound
import com.example.shopping_api.exception.UserAlreadyLoggedIn
import com.example.shopping_api.exception.UserNotFoundException
import com.example.shopping_api.mapper.TokenMapper
import com.example.shopping_api.mapper.UserMapper
import com.example.shopping_api.mapper.UserMapper.toEntity
import com.example.shopping_api.model.RefreshToken
import com.example.shopping_api.repository.RefreshTokenRepository
import com.example.shopping_api.repository.UserRepository
import com.example.shopping_api.security.JwtUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val tokenMapper: TokenMapper,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepository: RefreshTokenRepository,
    private val jwtUtil: JwtUtil
): AuthService {
    @Transactional(rollbackFor = [Exception::class])
    override fun register(request: RegisterRequest): RegisterResponse {
        if (userRepository.existsByEmail(request.email))
            throw EmailAlreadyExistsException(request.email)

        return userRepository.save(request
            .copy(password = passwordEncoder
                .encode(request.password)
                .toString())
            .toEntity())
            .let{userMapper.toResponse(it)}
    }

    override fun login(
        request: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException("${request.email} not found")

        if (!passwordEncoder.matches(request.password, user.password))
            throw InvalidRequestException("Invalid credentials")

        val activeToken = tokenRepository.findAllByUserAndExpiredIsFalseAndRevokedIsFalse(user)

        if (activeToken.isNotEmpty())
            throw UserAlreadyLoggedIn(user.id)

        val refreshToken = jwtUtil.generateRefreshToken(user.id, user.email)
        val token = RefreshToken(
            token = refreshToken,
            user = user
        )
        tokenRepository.save(token)

        val accessToken = jwtUtil.generateAccessToken(user.id, user.email)

        val refreshCookie = Cookie("refreshToken", refreshToken).apply {
            isHttpOnly = true
            secure = false
            path = "/"
            maxAge = 7 * 24 * 60 * 60
            setAttribute("SameSite", "Lax")
        }

        response.addCookie(refreshCookie)
        return userMapper.toResponse(accessToken, user)
    }

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val token = request.cookies?.firstOrNull { it.name == "refreshToken" }?.value
            ?: throw TokenNotFound()

        val storedToken = tokenRepository.findByToken(token)

        storedToken?.revoked = true
        storedToken?.expired = true

        tokenRepository.save(storedToken!!)

        SecurityContextHolder.clearContext()

        val cookie = Cookie("refreshToken", null)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = false
        cookie.maxAge = 0
        cookie.setAttribute("SameSite", "Lax")
        response.addCookie(cookie)
    }
    @Transactional(rollbackFor = [Exception::class])
    override fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): TokenResponse {

        val oldRefreshToken = request.cookies
            ?.firstOrNull { it.name == "refreshToken" }
            ?.value ?: throw TokenNotFound()

        val stored = tokenRepository.findByToken(oldRefreshToken)
            ?: throw TokenNotFound()

        if (!jwtUtil.isExpired(stored.token))
            throw TokenExpiredException()

        val user = stored.user

        val newAccessToken = jwtUtil.generateAccessToken(user.id, user.email)
        val newRefreshToken = jwtUtil.generateRefreshToken(user.id, user.email)

        stored.token = newRefreshToken
        stored.revoked = false
        stored.expired = false
        tokenRepository.save(stored)

        val refreshCookie = Cookie("refreshToken", newRefreshToken).apply {
            isHttpOnly = true
            secure = false
            path = "/"
            maxAge = 7 * 24 * 60 * 60
            setAttribute("SameSite", "Lax")
        }
        response.addCookie(refreshCookie)

        return tokenMapper.toResponse(newAccessToken)
    }
}