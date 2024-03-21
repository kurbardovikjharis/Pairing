package com.haris.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {

    private val _data = MutableStateFlow<ItemList?>(null)

    override val data: Flow<ItemList?> = _data

    override suspend fun getRecipes() {
        try {
            val response = api.getRecipes()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                _data.value = body
            } else {
                _data.value = null
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            _data.value = null
        }
    }
}