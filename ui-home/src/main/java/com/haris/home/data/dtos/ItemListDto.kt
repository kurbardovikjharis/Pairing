package com.haris.home.data.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemListDto(val count: Int?, val results: List<ItemDto>?)