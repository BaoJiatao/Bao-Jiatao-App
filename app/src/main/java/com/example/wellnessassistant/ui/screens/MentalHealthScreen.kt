package com.example.wellnessassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// Mental health screen for mood logging and meditation timer (planning feature)
@Composable
fun MentalHealthScreen() {
    Column {
        Text("Mental Health: Mood Tracker")
        Button(onClick = { /* Log mood */ }) { Text("Log Mood") }
        Button(onClick = { /* Start meditation */ }) { Text("Meditate") }
    }
}