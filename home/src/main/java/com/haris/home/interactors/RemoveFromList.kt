package com.haris.home.interactors

import com.haris.home.repositories.Repository
import javax.inject.Inject

internal class RemoveFromList @Inject constructor(
    private val repository: Repository
) {

    val flow = repository.data

    operator fun invoke(id: String) {
        repository.removeFromList(id)
    }
}