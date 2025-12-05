package com.example.shopping_api.exception

class UserAlreadyLoggedIn(id: Long):
    BaseException("User already logged in: $id", 400)