package com.example.wellnessassistant.network

import retrofit2.http.GET
import retrofit2.http.Query

// Response model for Edamam nutrition data
data class NutritionResponse(val calories: Int)

// Retrofit interface for Edamam API (nutrition lookup)
interface EdamamApi {
    @GET("api/nutrition-data")
    suspend fun getNutrition(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("ingr") ingredient: String
    ): NutritionResponse
}