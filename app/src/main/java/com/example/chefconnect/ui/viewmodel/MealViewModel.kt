package com.example.chefconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefconnect.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MealState>(MealState.Loading)
    val state: StateFlow<MealState> = _state

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
}