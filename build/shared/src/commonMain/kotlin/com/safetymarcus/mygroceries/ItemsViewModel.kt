package com.safetymarcus.mygroceries

import com.safetymarcus.mygroceries.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel(private val repository: ItemsRepository) {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    suspend fun onResume() {
        val loaded = repository.getAllItems().sortedBy { it.name.lowercase() }
        _items.value = loaded
    }
}
