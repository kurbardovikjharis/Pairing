package com.haris.home

import kotlinx.coroutines.flow.Flow

interface Repository {

    val data: Flow<ItemList?>

    suspend fun getRecipes()
}