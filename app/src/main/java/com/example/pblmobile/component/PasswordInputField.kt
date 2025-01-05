package com.example.pblmobile.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun PasswordInputField(onValueChange: (String) -> Unit) {
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onValueChange(it)
        },
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Masukkan password") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
    )
}