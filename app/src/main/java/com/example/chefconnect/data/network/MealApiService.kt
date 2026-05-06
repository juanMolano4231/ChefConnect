package com.example.chefconnect.data.network

import com.example.chefconnect.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealResponse

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") id: String
    ): MealDetailResponse
}