package com.example.shopping_api.exception

class EmptyCartException() :

    BaseException("Cart is Empty! ", 400)