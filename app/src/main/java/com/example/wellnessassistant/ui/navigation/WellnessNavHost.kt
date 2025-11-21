package com.example.wellnessassistant.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Text
import com.example.wellnessassistant.ui.screens.DashboardScreen
import com.example.wellnessassistant.ui.screens.DietScreen  // Integrated with Room
import com.example.wellnessassistant.ui.screens.FitnessScreen
import com.example.wellnessassistant.ui.screens.MentalHealthScreen
import com.example.wellnessassistant.ui.screens.ProfileScreen

// Navigation host for screen routing (assignment navigation requirement)
@Composable
fun WellnessNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier.fillMaxSize()  // Apply modifier and ensure full size
    ) {
        composable("dashboard") { DashboardScreen() }  // Home tab
        composable("fitness") { FitnessScreen() }  // Fitness tab
        composable("diet") { DietScreen() }  // Diet tab with Room + Edamam integration
        composable("mental") { MentalHealthScreen() }  // Mental health tab
        composable("profile") { ProfileScreen() }  // Profile tab
    }
}