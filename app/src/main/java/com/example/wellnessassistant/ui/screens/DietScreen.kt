package com.example.wellnessassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.example.wellnessassistant.data.AppDatabase
import com.example.wellnessassistant.data.Meal
import com.example.wellnessassistant.network.RetrofitInstance
import kotlinx.coroutines.launch

// Diet screen for meal logging with Room and Edamam API (planning core feature, offline support)
@Composable
fun DietScreen() {
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "wellness-db").build() }  // Build Room DB for local storage
    val dao = db.mealDao()
    val meals by dao.getAllMeals().collectAsState(initial = emptyList())  // Reactive list from Room
    var name by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text("Diet Tracking (Room + API)")
        TextField(value = name, onValueChange = { name = it }, label = { Text("Ingredient Name") })
        Button(onClick = {
            coroutineScope.launch {
                try {
                    // Call Edamam API for nutrition data
                    val response = RetrofitInstance.api.getNutrition(RetrofitInstance.APP_ID, RetrofitInstance.APP_KEY, name)
                    // Insert into Room for persistent storage
                    dao.insertMeal(Meal(name = name, calories = response.calories))
                    name = ""
                } catch (e: Exception) {
                    // Handle API error (e.g., Toast "Network error", fallback to cached Room data)
                }
            }
        }) { Text("Add Meal & Query Nutrition") }
        LazyColumn {
            items(meals) { meal ->
                Text("${meal.name}: ${meal.calories} cal (Added: ${java.text.SimpleDateFormat("HH:mm").format(java.util.Date(meal.date))})")
            }
        }
    }
}