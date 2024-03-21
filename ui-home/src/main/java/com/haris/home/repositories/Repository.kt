package com.haris.home.repositories

import com.haris.data.Result
import com.haris.home.data.entities.Item
import kotlinx.coroutines.flow.Flow

interface Repository {

    val data: Flow<Result<List<Item>>>

    suspend fun getRecipes()
}