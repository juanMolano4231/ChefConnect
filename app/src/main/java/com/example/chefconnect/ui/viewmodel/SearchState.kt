package com.example.chefconnect.ui.viewmodel

import com.example.chefconnect.data.model.Meal

sealed class SearchState {

    object Idle : SearchState()

    object Loading : SearchState()

    data class Success(
        val meals: List<Meal>
    ) : SearchState()

    object Empty : SearchState()

    data class Error(
        val message: String
    ) : SearchState()
}