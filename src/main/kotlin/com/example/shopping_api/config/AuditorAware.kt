package com.example.shopping_api.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Optional

@Component("auditorAware")
class AuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val auth = SecurityContextHolder.getContext().authentication
        return when {
            auth == null || !auth.isAuthenticated || auth is AnonymousAuthenticationToken ->
                Optional.of("system-admin")
            auth.principal is UserDetails ->
                Optional.of((auth.principal as UserDetails).username)
            else ->
                Optional.ofNullable(auth.name)
        }
    }
}