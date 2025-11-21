package com.example.wellnessassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient

// Fitness screen for step/heart rate tracking (planning feature with Health Connect)
@Composable
fun FitnessScreen() {
    val context = LocalContext.current
    Column {
        Text("Fitness Tracking")
        Button(onClick = {
            // Placeholder for Health Connect sync
            val client = HealthConnectClient.getOrCreate(context)
            // client.readRecords(...) // Full impl requires permissions
        }) { Text("Sync Steps") }
    }
}