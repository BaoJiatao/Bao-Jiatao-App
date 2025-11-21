package com.example.wellnessassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Profile screen for settings like dark mode toggle (planning UI feature)
@Composable
fun ProfileScreen() {
    var darkMode by remember { mutableStateOf(false) }
    Column {
        Text("Profile: Settings")
        Row {
            Text("Dark Mode")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = darkMode,
                onCheckedChange = { checked ->
                    darkMode = checked
                }
            )
        }
    }
}