package com.safetymarcus.mygroceries.server

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

data class Item(
    val name: String = "",
    val brand: String = "",
    val description: String = "",
    val size: String = "",
)

object ItemsTable : IntIdTable("items") {
    val name = varchar("name", 50)
    val brand = varchar("brand", 50)
    val size = varchar("size", 50)
}

class ItemsDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemsDao>(ItemsTable)

    var name by ItemsTable.name
    var brand by ItemsTable.brand
    var size by ItemsTable.size
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: ItemsDao) = Item(
    dao.name, dao.brand, dao.size
)
