package com.haris.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.data.Result
import com.haris.home.data.entities.Item
import com.haris.home.interactors.GetRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRecipes: GetRecipes
) : ViewModel() {

    init {
        viewModelScope.launch {
            getRecipes()
        }
    }

    private val itemsRemoved = MutableStateFlow<List<String>>(emptyList())
    val state: StateFlow<ViewState> =
        combine(getRecipes.flow, itemsRemoved) { recipes, itemsRemoved ->
            when (recipes) {
                is Result.Success -> {
                    val data = recipes.data?.toMutableList() ?: mutableListOf()
                    data.removeIf { itemsRemoved.contains(it.canonicalId) }
                    ViewState.Success(data)
                }

                is Result.Loading -> {
                    val data = recipes.data?.toMutableList() ?: mutableListOf()
                    data.removeIf { itemsRemoved.contains(it.canonicalId) }
                    ViewState.Loading(data)
                }

                is Result.Error -> {
                    val data = recipes.data?.toMutableList() ?: mutableListOf()
                    data.removeIf { itemsRemoved.contains(it.canonicalId) }
                    ViewState.Error(
                        message = recipes.message ?: "",
                        data
                    )
                }

                is Result.None -> ViewState.Empty
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ViewState.Empty
        )

    fun removeItem(id: String) {
        val list = itemsRemoved.value.toMutableList()
        list.add(id)

        itemsRemoved.update { list }
    }

    fun retry() {

    }
}

@Immutable
internal sealed interface ViewState {

    data class Success(
        val items: List<Item>
    ) : ViewState

    data class Error(
        val message: String,
        val items: List<Item>
    ) : ViewState

    data class Loading(
        val items: List<Item>
    ) : ViewState

    data object Empty : ViewState
}