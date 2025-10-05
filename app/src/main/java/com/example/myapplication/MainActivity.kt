// Final MainActivity.kt for WellTrack App
// Implements a unified health and wellness assistant using Jetpack Compose.
// Key features: Dashboard with stats and charts, bottom navigation for sections,
// fitness/diet/mental health tracking, personalized plans, emergency response,
// local auth placeholder, dark/light toggle.
// Uses Navigation Component for screens; placeholders for Room DB, Health Connect, ML Kit, Edamam.
// For full build: Add dependencies in build.gradle (e.g., navigation-compose, room-runtime).

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

// Sample data for logs and plans
data class LogItem(val title: String, val details: String, val progress: Int)
data class HealthPlan(val icon: String, val title: String, val desc: String)

val sampleLogs = listOf(
    LogItem("Steps Today", "8,500 / 10k via sensors", 85),
    LogItem("Workout Session", "30 min run, HR 120 bpm", 90),
    LogItem("Sleep Log", "7 hrs tracked", 75)
)
val sampleMeals = listOf(
    LogItem("Breakfast", "Oatmeal (400 cal)", 200),
    LogItem("Lunch", "Salad - Barcode scanned", 350)
)
val sampleMoods = listOf(
    LogItem("Morning Mood", "Happy - Timer used", 90),
    LogItem("Evening Log", "Stressed - Suggestion applied", 60)
)
val samplePlans = listOf(
    HealthPlan("📅", "Daily Steps Goal", "10,000 steps – 85% complete, reminder set"),
    HealthPlan("🍲", "Meal Plan", "Balanced diet – 2,000 cal target"),
    HealthPlan("🧘", "Mindfulness", "10 min session – Progress update")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController, currentRoute)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeDashboard() }
                        composable("fitness") { FitnessScreen() }
                        composable("diet") { DietScreen() }
                        composable("mental") { MentalScreen() }
                        composable("profile") { ProfileScreen() }
                    }
                }
            }
        }
    }
}

// Bottom Navigation Bar for quick section access
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar {
        val routes = listOf("home" to "Home", "fitness" to "Fitness", "diet" to "Diet", "mental" to "Mental", "profile" to "Profile")
        val icons = listOf(Icons.Default.Home, Icons.Default.FitnessCenter, Icons.Default.Restaurant, Icons.Default.Mood, Icons.Default.Person)
        routes.forEachIndexed { index, (route, label) ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = label) },
                label = { Text(label) },
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Home Dashboard: Unified view with stats, charts, summaries
@Composable
fun HomeDashboard() {
    var showEmergency by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Health Data Analysis: Chart placeholder for trends
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Wellness Dashboard",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Interactive Trends Chart\n(Fitness: 85% | Diet: 90% | Mental: 75%)",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Daily Summary: Great progress – Data synced locally",
                        color = Color.Green,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Personalized Health Plans: Tailored recommendations
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    Text(
                        "Personalized Plans",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(samplePlans) { plan ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* Navigate to plan details */ }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = plan.icon,
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(plan.title, fontWeight = FontWeight.Medium)
                                    Text(plan.desc, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Emergency Response Button
        item {
            Button(
                onClick = { showEmergency = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Call, contentDescription = "Emergency", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🚨 Emergency Response", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // Emergency Dialog
    if (showEmergency) {
        AlertDialog(
            onDismissRequest = { showEmergency = false },
            title = { Text("Emergency Help") },
            text = { Text("Calling contacts or providing quick info...") },
            confirmButton = {
                TextButton(onClick = { /* Launch call intent */ showEmergency = false }) {
                    Text("Call Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEmergency = false }) { Text("Cancel") }
            }
        )
    }
}

// Fitness Screen: Track activities with sensors or manual input
@Composable
fun FitnessScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleLogs) { log ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, modifier = Modifier.padding(end = 12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(log.title, fontWeight = FontWeight.Medium)
                        Text(log.details, style = MaterialTheme.typography.bodySmall)
                    }
                    Text("${log.progress}%", color = Color.Blue, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
    // Placeholder for sensor integration
    Text(
        "Auto-track with device sensors",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(16.dp)
    )
}

// Diet Screen: Log meals with barcode scanning and nutrition calc
@Composable
fun DietScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Diet & Nutrition Log",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Launch camera for barcode */ },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Scan Barcode for Meal")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sampleMeals) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.padding(end = 12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(log.title, fontWeight = FontWeight.Medium)
                            Text(log.details, style = MaterialTheme.typography.bodySmall)
                        }
                        Text("${log.progress} cal", color = Color.Green)
                    }
                }
            }
        }
    }
}

// Mental Health Screen: Mood and sleep monitoring with timers
@Composable
fun MentalScreen() {
    var selectedMood by remember { mutableStateOf("😊 Happy") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Mental Health & Sleep Monitor",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Mood tracking buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val moods = listOf("😊 Happy", "😢 Sad", "😐 Neutral", "😠 Stressed")
            moods.forEach { mood ->
                FilterChip(
                    selected = selectedMood == mood,
                    onClick = { selectedMood = mood },
                    label = { Text(mood) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Current Mood: $selectedMood | Suggestion: Guided session if stressed",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { /* Set timer and reminder */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Start Timer & Reminder")
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(sampleMoods) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        log.title + ": " + log.details + " (${log.progress}%)",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

// Profile Screen: Secure auth, goals, and theme toggle
@Composable
fun ProfileScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var showLogin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Profile & Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { showLogin = true }) {
            Text("Sign Up / Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark/Light Mode: ")
            Switch(checked = darkMode, onCheckedChange = { darkMode = it })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Set Personalized Goals:\n• Daily Steps: 10k\n• Cal Intake: 2,000\n• Mindfulness: 10 min",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }

    // Login Dialog Placeholder
    if (showLogin) {
        AlertDialog(
            onDismissRequest = { showLogin = false },
            title = { Text("Secure Login") },
            text = { Text("Local storage for profiles and logs") },
            confirmButton = {
                TextButton(onClick = { /* Auth logic */ showLogin = false }) {
                    Text("Login")
                }
            }
        )
    }
}

// Preview for quick visualization
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyApplicationTheme {
        HomeDashboard()
    }
}