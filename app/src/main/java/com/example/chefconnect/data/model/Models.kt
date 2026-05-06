package com.example.chefconnect.data.model

data class MealResponse(
    val meals: List<Meal>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class CategoryResponse(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String
)

data class MealDetailResponse(
    val meals: List<MealDetail>
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String? = null
)