package com.example.shopping_api.util

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

@Suppress("UNCHECKED_CAST")
fun <T : Any> PagedModel<*>.cast(): PagedModel<EntityModel<T>> =
    this as PagedModel<EntityModel<T>>