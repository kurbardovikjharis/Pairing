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
        cookTimeMinutes = this.cookTimeMinutes ?: 0.0,
        totalTimeMinutes = this.totalTimeMinutes ?: 0.0,
        canonicalId = this.canonicalId ?: "",
        description = this.description ?: ""
    )