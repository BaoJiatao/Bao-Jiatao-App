package com.example.wellnessassistant.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlin.Triple

// Bottom navigation bar with tabs (planning UI feature)
@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    NavigationBar {
        val routes: List<Triple<String, ImageVector, String>> = listOf(
            Triple("dashboard", Icons.Filled.Home, "Home"),
            Triple("fitness", Icons.Filled.Add, "Fitness"),
            Triple("diet", Icons.Filled.Restaurant, "Diet"),
            Triple("mental", Icons.Filled.Favorite, "Mental"),
            Triple("profile", Icons.Filled.Person, "Profile")
        )
        routes.forEach { triple: Triple<String, ImageVector, String> ->
            val route: String = triple.first
            val icon: ImageVector = triple.second
            val label: String = triple.third
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = null) },
                label = { Text(text = label) },
                selected = currentRoute?.destination?.route == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}