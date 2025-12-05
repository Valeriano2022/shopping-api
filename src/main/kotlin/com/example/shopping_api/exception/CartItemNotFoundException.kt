package com.example.shopping_api.exception

class CartItemNotFoundException() :

    BaseException("Cart Item not found", 404)