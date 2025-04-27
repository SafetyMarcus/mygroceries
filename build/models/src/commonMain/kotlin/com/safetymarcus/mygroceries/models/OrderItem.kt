package com.safetymarcus.mygroceries.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val id: Long = 0,
    val itemId: Int = 0,
    val marketOrderId: Int = 0,
    val quantity: Int = 0,
    val itemTotalPrice: Double = 0.0,
    val itemSaving: Double = 0.0,
    val unitPrice: Double = 0.0,
)