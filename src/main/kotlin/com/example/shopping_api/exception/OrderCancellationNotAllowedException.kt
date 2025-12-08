package com.example.shopping_api.exception

class OrderCancellationNotAllowedException :
    BaseException("Order is older than 7 days and cannot be cancelled.", 400)
