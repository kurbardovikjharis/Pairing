package com.haris.home.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ItemListDto(val count: Int?, val results: List<ItemDto>?)