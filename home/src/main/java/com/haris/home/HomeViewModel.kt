package com.haris.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.data.Result
import com.haris.home.data.entities.Item
import com.haris.home.interactors.GetRecipes
import com.haris.home.interactors.RemoveFromList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRecipes: GetRecipes,
    private val removeFromList: RemoveFromList
) : ViewModel() {

    init {
        viewModelScope.launch {
            getRecipes()
        }
    }

    val state: StateFlow<ViewState> = getRecipes.flow.map {
        when (it) {
            is Result.Success -> {
                ViewState.Success(it.data ?: emptyList())
            }

            is Result.Loading -> {
                ViewState.Loading(it.data ?: emptyList())
            }

            is Result.Error -> {
                ViewState.Error(
                    message = it.message ?: "",
                    it.data ?: emptyList()
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
        removeFromList(id)
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