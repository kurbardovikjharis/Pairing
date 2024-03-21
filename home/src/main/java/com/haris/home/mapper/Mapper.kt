package com.haris.home.mapper

import com.haris.home.data.dtos.ItemDto
import com.haris.home.data.dtos.ItemListDto
import com.haris.home.data.entities.Item
import com.haris.home.data.entities.ItemList

internal fun ItemListDto.toItemList(): ItemList =
    ItemList(
        count = this.count ?: 0,
        results = this.results?.map { it.toItem() } ?: emptyList()
    )

private fun ItemDto.toItem(): Item =
    Item(
        name = this.name ?: "",
        cookTimeMinutes = (this.cook_time_minutes ?: 0.0).toString(),
        totalTimeMinutes = (this.total_time_minutes ?: 0.0).toString(),
        canonicalId = this.canonical_id ?: "",
        description = this.description ?: ""
    )