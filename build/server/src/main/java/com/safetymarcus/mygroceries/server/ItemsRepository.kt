package com.safetymarcus.mygroceries.server

import com.safetymarcus.mygroceries.models.Item
import org.slf4j.LoggerFactory

object ItemsRepository {

    suspend fun getItems(): List<Item> = suspendTransaction {
        LoggerFactory.getLogger("items").info("")
        ItemsDao.all().map(::daoToModel)
    }
}