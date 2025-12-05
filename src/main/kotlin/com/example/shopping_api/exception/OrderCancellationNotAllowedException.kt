package com.example.shopping_api.exception

class OrderCancellationNotAllowedException :
    BaseException("Order cannot be cancelled yet.", 400)
