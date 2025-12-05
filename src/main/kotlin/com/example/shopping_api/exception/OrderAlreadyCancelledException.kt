package com.example.shopping_api.exception

class OrderAlreadyCancelledException :
    BaseException("Order already cancelled", 400)
