package com.example.shopping_api.exception

class ForbiddenException(message: String) :
    BaseException(message, 403)