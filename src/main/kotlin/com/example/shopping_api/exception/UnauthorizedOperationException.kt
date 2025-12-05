package com.example.shopping_api.exception

class UnauthorizedOperationException(message: String) :
    BaseException(message, 401)