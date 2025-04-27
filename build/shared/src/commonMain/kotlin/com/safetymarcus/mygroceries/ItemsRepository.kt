package com.safetymarcus.mygroceries

import com.safetymarcus.mygroceries.models.Item
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

interface ItemsRepository {
    suspend fun getAllItems(): List<Item>
}

class KtorItemsRepository : KtorRepository(), ItemsRepository {
    override suspend fun getAllItems(): List<Item> {
        val response: HttpResponse = get("/items")
        return response.body()
    }
}
