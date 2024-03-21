package com.haris.home.data.entities

data class Item(
    val name: String,
    val cookTimeMinutes: Double,
    val totalTimeMinutes: Double,
    val canonicalId: String,
    val description: String,
)