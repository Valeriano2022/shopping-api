package com.example.shopping_api

import com.example.shopping_api.config.JpaAuditingConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Import(JpaAuditingConfig::class)
class ShoppingApiApplication

fun main(args: Array<String>) {
	runApplication<ShoppingApiApplication>(*args)
}
