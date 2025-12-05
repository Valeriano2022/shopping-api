package com.example.shopping_api.exception

class OrderNotFoundException() :

    BaseException("Order not found", 404)