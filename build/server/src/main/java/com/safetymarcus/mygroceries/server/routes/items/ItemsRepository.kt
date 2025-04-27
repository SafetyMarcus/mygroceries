package com.safetymarcus.mygroceries.server.routes.items

import com.safetymarcus.mygroceries.models.Item
import com.safetymarcus.mygroceries.server.routes.orderitems.OrderItemsTable
import com.safetymarcus.mygroceries.server.routes.orderitems.suspendTransaction
import org.jetbrains.exposed.sql.sum
import org.slf4j.LoggerFactory

object ItemsRepository {

    suspend fun getItems(): List<Item> = suspendTransaction {
        LoggerFactory.getLogger("items").info("")
        val sumPurchases = OrderItemsTable.quantity.sum()
        val purchaseFrequencies = OrderItemsTable.select(
            OrderItemsTable.itemId,
            sumPurchases
        ).groupBy(OrderItemsTable.itemId)
            .associate { it[OrderItemsTable.itemId] to it[sumPurchases] }
        ItemsDao.all().map { item ->
            daoToModel(item, purchaseFrequencies[item.id] ?: 0 )
        }
    }
}