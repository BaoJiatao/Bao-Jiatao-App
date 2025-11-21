package com.example.wellnessassistant.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Room database for local persistence (supports offline mode)
@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao  // Abstract DAO access
}