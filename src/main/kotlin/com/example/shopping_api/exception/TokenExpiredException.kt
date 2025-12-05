package com.example.shopping_api.exception

class TokenExpiredException():
    BaseException("Token is either expired!", 404)