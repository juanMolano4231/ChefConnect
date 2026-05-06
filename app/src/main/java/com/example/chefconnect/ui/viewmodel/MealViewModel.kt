package com.example.chefconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefconnect.data.model.MealDetail
import com.example.chefconnect.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MealState>(MealState.Loading)
    val state: StateFlow<MealState> = _state

    private val _searchState = MutableStateFlow<MealState>(MealState.Loading)
    val searchState: StateFlow<MealState> = _searchState

    private val _detailState = MutableStateFlow<MealDetail?>(null)
    val detailState: StateFlow<MealDetail?> = _detailState

    fun loadCategories() {
        viewModelScope.launch {
            _state.value = MealState.Loading
            try {
                val response = repository.getCategories()
                _state.value = MealState.SuccessCategories(response.categories)
            } catch (e: Exception) {
                _state.value = MealState.Error(e.message ?: "Error")
            }
        }
    }

    fun loadMeals(category: String) {
        viewModelScope.launch {
            _state.value = MealState.Loading
            try {
                val response = repository.getMeals(category)
                _state.value = MealState.SuccessMeals(response.meals)
            } catch (e: Exception) {
                _state.value = MealState.Error(e.message ?: "Error")
            }
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchMeals(query)

                if (result.meals.isNullOrEmpty()) {
                    _searchState.value = MealState.Error("No results")
                } else {
                    _searchState.value = MealState.SuccessMeals(result.meals)
                }

            } catch (e: Exception) {
                _searchState.value = MealState.Error("Network error")
            }
        }
    }

    fun loadDetail(id: String) {
        viewModelScope.launch {
            try {
                val res = repository.getMealDetail(id)
                _detailState.value = res.meals.firstOrNull()
            } catch (_: Exception) {
            }
        }
    }

    suspend fun getDetail(id: String) =
        repository.getMealDetail(id)
}