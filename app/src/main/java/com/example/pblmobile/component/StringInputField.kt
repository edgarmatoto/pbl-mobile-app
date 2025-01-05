package com.example.pblmobile.component

import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun StringInputField(data: String, label: String, placeholder: String, onValueChange: (String) -> Unit) {
    var inputData by remember { mutableStateOf(data) }

    OutlinedTextField(
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth(),
        value = inputData,
        onValueChange = {
            inputData = it
            onValueChange(it) // Update the state in the parent composable
        }
    )
}