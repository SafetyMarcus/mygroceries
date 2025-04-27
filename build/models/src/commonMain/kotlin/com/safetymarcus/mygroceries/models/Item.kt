package com.safetymarcus.mygroceries.models

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val name: String = "",
    val brand: String = "",
    val description: String = "",
    val size: String = "",
    val volume: Int = 0,
)