package com.haris.home.repositories

import com.haris.data.Result
import com.haris.home.data.entities.Item
import com.haris.home.mapper.toItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {

    private val _data = MutableStateFlow<Result<List<Item>>>(Result.None())
    override val data: Flow<Result<List<Item>>> = _data

    override suspend fun getRecipes() {
        val cachedData = _data.value.data
        _data.value = Result.Loading(cachedData)
        try {
            val result = api.getRecipes()
            val data = result.body()

            if (result.isSuccessful && data != null) {
                _data.value = Result.Success(data.toItemList().results)
            } else {
                _data.value = Result.Error(
                    message = "no data",
                    data = cachedData
                )
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            _data.value = Result.Error(
                message = exception.message,
                data = cachedData
            )
        }
    }

    override fun removeFromList(id: String) {
        val result = _data.value
        if (result !is Result.Success) return

        val mutableList = result.data?.toMutableList() ?: return
        val item = mutableList.find { it.canonicalId == id }
        mutableList.remove(item)

        _data.update {
            Result.Success(mutableList)
        }
    }
}