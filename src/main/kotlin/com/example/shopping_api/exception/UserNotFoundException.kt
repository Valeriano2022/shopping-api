package com.example.shopping_api.exception

class UserNotFoundException(email: String? = null, id: Long? = null) :

    BaseException("User not found: ${email ?: id}", 404)