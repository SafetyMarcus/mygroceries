package com.safetymarcus.mygroceries.server.routes.items

import com.safetymarcus.mygroceries.models.Item
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ItemsTable : IntIdTable("items") {
    val name = text("name")
    val brand = text("brand")
    val description = text("description")
    val size = text("size")
}

class ItemsDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemsDao>(ItemsTable)

    var name by ItemsTable.name
    var brand by ItemsTable.brand
    var description by ItemsTable.description
    var size by ItemsTable.size
}

@Suppress("MemberExtensionConflict")
fun daoToModel(dao: ItemsDao, volume: Int) = Item(
    name = dao.name,
    brand = dao.brand,
    description = dao.size,
    volume = volume,
)