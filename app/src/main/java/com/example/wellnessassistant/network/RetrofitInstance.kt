package com.example.wellnessassistant.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton for Retrofit instance (lazy initialization for API calls)
object RetrofitInstance {
    private const val BASE_URL = "https://api.edamam.com/"

    val api: EdamamApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Gson for JSON parsing
            .build()
            .create(EdamamApi::class.java)
    }

    const val APP_ID = "your_app_id"  // Replace with Edamam app ID
    const val APP_KEY = "your_app_key"  // Replace with Edamam app key
}