package com.example.shopping_api.exception

class InvalidRequestException(message: String) :
    BaseException(message, 400)