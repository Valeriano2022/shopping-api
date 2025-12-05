package com.example.shopping_api.exception

class EmailAlreadyExistsException(email: String) :
    BaseException("Email already exists: $email", 409)