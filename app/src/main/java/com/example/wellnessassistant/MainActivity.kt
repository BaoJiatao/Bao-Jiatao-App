package com.example.wellnessassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold  // ✅ Material3 Scaffold
import androidx.compose.material3.MaterialTheme  // ✅ Material3 主题
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.wellnessassistant.ui.navigation.BottomNavBar
import com.example.wellnessassistant.ui.navigation.WellnessNavHost
import com.example.wellnessassistant.ui.theme.WellnessAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

// Main activity with Compose navigation and Hilt (assignment lifecycle management)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessAssistantTheme {
                MaterialTheme {  // ✅ 包裹在 Material3 主题中（无需 @Composable 注解）
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavBar(navController) }
                    ) { innerPadding: PaddingValues ->
                        Box(modifier = Modifier.padding(innerPadding)) {  // ✅ 无需 @Composable 注解
                            WellnessNavHost(navController)
                        }
                    }
                }
            }
        }
    }
}