package com.example.wellnessassistant.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Room DAO for meal CRUD operations
@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY date DESC")
    fun getAllMeals(): Flow<List<Meal>>  // Flow for reactive UI updates

    @Insert
    suspend fun insertMeal(meal: Meal)  // Suspend for Coroutines
}