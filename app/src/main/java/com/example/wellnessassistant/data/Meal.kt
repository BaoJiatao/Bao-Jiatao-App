package com.example.wellnessassistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity for meal logs (persistent storage for diet tracking)
@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,  // Meal name (e.g., "apple")
    val calories: Int,  // Calories from Edamam API
    val date: Long = System.currentTimeMillis()  // Timestamp
)