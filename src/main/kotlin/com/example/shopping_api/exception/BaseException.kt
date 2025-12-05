package com.example.shopping_api.exception

open class BaseException(
    override val message: String,
    val status: Int
) : RuntimeException(message)