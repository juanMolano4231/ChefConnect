package com.example.chefconnect.data.repository

import com.example.chefconnect.data.network.MealApiService

class MealRepository(
    private val api: MealApiService
) {

    suspend fun getCategories() = api.getCategories()

    suspend fun getMeals(category: String) =
        api.getMealsByCategory(category)


}