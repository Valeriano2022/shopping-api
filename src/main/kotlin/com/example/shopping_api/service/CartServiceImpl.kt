package com.example.shopping_api.service

import com.example.shopping_api.dto.cart.AddToCartRequest
import com.example.shopping_api.dto.cart.CartItemResponse
import com.example.shopping_api.dto.cart.CartResponse
import com.example.shopping_api.dto.cart.UpdateCartItemRequest
import com.example.shopping_api.exception.CartItemNotFoundException
import com.example.shopping_api.exception.CartNotFoundException
import com.example.shopping_api.exception.ProductNotFoundException
import com.example.shopping_api.exception.UnauthorizedOperationException
import com.example.shopping_api.mapper.CartItemMapper
import com.example.shopping_api.mapper.CartMapper
import com.example.shopping_api.model.CartItem
import com.example.shopping_api.repository.CartItemRepository
import com.example.shopping_api.repository.CartRepository
import com.example.shopping_api.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartMapper: CartMapper,
    private val cartItemMapper: CartItemMapper,
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository
): CartService {
    @Transactional(readOnly = true)
    override fun getCart(userId: Long): CartResponse {
        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()
        val items = cart.items
        return cartMapper.toResponse(cart, items)
    }
    @Transactional(rollbackFor = [Throwable::class])
    override fun add(
        userId: Long,
        request: AddToCartRequest
    ): CartResponse {

        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val product = productRepository.findById(request.productId)
            .orElseThrow { ProductNotFoundException() }

        val existingItem = cartItemRepository.findByCartIdAndProductId(cart.id, product.id)

        if (existingItem != null) {
            existingItem.quantity += request.quantity
            existingItem.subtotal = existingItem.quantity * product.price
            cartItemRepository.save(existingItem)
        } else {
            val newItem = CartItem(
                cart = cart,
                product = product,
                quantity = request.quantity,
                subtotal = product.price * request.quantity
            )
            cartItemRepository.save(newItem)
        }

        val updatedItems = cartItemRepository.findByCartId(cart.id)

        return cartMapper.toResponse(cart, updatedItems)
    }

    @Transactional(rollbackFor = [Throwable::class])
    override fun update(
        userId: Long,
        request: UpdateCartItemRequest?
    ): CartItemResponse {

        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val item = cartItemRepository.findById(request?.cartItemId!!)
            .orElseThrow { CartItemNotFoundException() }

        if (item.cart.id != cart.id)
            throw UnauthorizedOperationException("Forbidden.")

        if (request.quantity <= 0) {
            cartItemRepository.delete(item)
        }

        item.quantity = request.quantity
        item.subtotal = item.quantity * item.product.price

        val updatedItem = cartItemRepository.save(item)

        return cartItemMapper.toResponse(updatedItem)
    }

    @Transactional(rollbackFor = [Throwable::class])
    override fun remove(userId: Long, cartItemId: Long) {
        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val item = cartItemRepository.findById(cartItemId)
            .orElseThrow { CartItemNotFoundException() }

        if (item.cart.id != cart.id)
            throw UnauthorizedOperationException("Forbidden.")

        cartItemRepository.delete(item)
    }
    @Transactional(rollbackFor = [Throwable::class])
    override fun removeByProduct(userId: Long, productId: Long) {
        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException() }

        val cartItem = cartItemRepository.findByCartIdAndProductId(cart.id, product.id)
            ?:throw CartItemNotFoundException()

        cartItemRepository.delete(cartItem)
    }

    @Transactional(rollbackFor = [Throwable::class])
    override fun clear(userId: Long) {
        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()
        cartItemRepository.deleteAllByCartId(cart.id)
    }
}