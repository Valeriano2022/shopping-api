package com.example.shopping_api.scheduler

import com.example.shopping_api.repository.OrderRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderStatusScheduler(
    private val orderRepository: OrderRepository,
) {

    private val log = LoggerFactory.getLogger(OrderStatusScheduler::class.java)

    /**
     * Runs every day at midnight (00:00)
     * Updates all orders older than 7 days to PENDING
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    fun updateOldOrders() {
        val sevenDaysAgo = LocalDateTime.now().minusDays(7)

        val oldOrders = orderRepository.findByCreatedAtBeforeAndStatusNot(
            sevenDaysAgo,
            "PENDING"
        )

        if (oldOrders.isEmpty()) {
            log.info("No orders require status update.")
            return
        }

        oldOrders.forEach { order ->
            if(order.status != "CANCELLED")
                order.status = "PENDING"
        }

        orderRepository.saveAll(oldOrders)

        log.info("Updated ${oldOrders.size} orders to PENDING (older than 7 days).")
    }
}
