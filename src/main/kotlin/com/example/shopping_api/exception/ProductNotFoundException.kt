package com.example.shopping_api.exception

class ProductNotFoundException() :

    BaseException("Product not found", 404)