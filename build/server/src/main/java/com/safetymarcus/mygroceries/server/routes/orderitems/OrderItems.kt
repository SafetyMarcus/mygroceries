package com.safetymarcus.mygroceries.server.routes.orderitems

import com.safetymarcus.mygroceries.models.OrderItem
import com.safetymarcus.mygroceries.server.routes.items.ItemsDao
import com.safetymarcus.mygroceries.server.routes.items.ItemsTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object OrderItemsTable : LongIdTable("order_items") {
    val itemId = reference("item_id", ItemsTable)
    val marketOrderId = integer("market_order_id")
    val quantity = integer("quantity")
    val itemTotalPrice = decimal("item_total_price", 10, 2)
    val itemSaving = decimal("item_saving", 10, 2)
    val unitPrice = decimal("unit_price", 10, 2)
}



class OrderItemsDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<OrderItemsDao>(OrderItemsTable)

    var itemId by ItemsDao referencedOn OrderItemsTable.itemId
    var marketOrderId by OrderItemsTable.marketOrderId
    var quantity by OrderItemsTable.quantity
    var itemTotalPrice by OrderItemsTable.itemTotalPrice
    var itemSaving by OrderItemsTable.itemSaving
    var unitPrice by OrderItemsTable.unitPrice
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: OrderItemsDao) = OrderItem(
    id = dao.id.value,
    itemId = dao.itemId.id.value,
    marketOrderId = dao.marketOrderId,
    quantity = dao.quantity,
    itemTotalPrice = dao.itemTotalPrice.toDouble(), //TODO look to expose BigDecimal to clients
    itemSaving = dao.itemSaving.toDouble(),
    unitPrice = dao.unitPrice.toDouble(),
)
