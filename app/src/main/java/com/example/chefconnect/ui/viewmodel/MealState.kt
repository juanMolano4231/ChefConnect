package com.example.chefconnect.ui.viewmodel

import com.example.chefconnect.data.model.*

sealed class MealState {
    object Loading : MealState()
    data class SuccessMeals(val meals: List<Meal>) : MealState()
    data class SuccessCategories(val categories: List<Category>) : MealState()
    data class Error(val message: String) : MealState()
}