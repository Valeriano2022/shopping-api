package com.example.shopping_api.exception

class CartNotFoundException():
    BaseException("Cart not found", 404)