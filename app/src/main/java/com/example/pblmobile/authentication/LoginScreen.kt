package com.example.pblmobile.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.user.LoginRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.utils.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun UsernameInputField(onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(text = "Username") },
        placeholder = { Text(text = "prabowo") },
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onValueChange(it) // Update the state in the parent composable
        }
    )
}

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


@Composable
fun RegisterText(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier.fillMaxWidth().padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Belum punya akun? ")
        Text(
            text = "Daftar", color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                onClick()
            }, textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Masuk dengan akun",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            ) {
                UsernameInputField { username = it }
                Spacer(Modifier.size(5.dp))
                PasswordInputField { password = it }
                Spacer(Modifier.size(20.dp))

                // Handle login on button click
                PrimaryButton(text = "Login") {
                    coroutineScope.launch {
                        handleLogin(username, password, context, navController)
                    }
                }
            }
        },
        bottomBar = {
            RegisterText {
                // navigate to register screen
                navController.navigate("register")
            }
        }
    )
}

suspend fun handleLogin(username: String, password: String, context: Context, navController: NavController) {
    try {
        val loginRequest = LoginRequest(username, password)
        val response = RetrofitInstance.api.loginUser(loginRequest)

        if (response.status) {
            val userId = response.user.id
            val email = response.user.email
            val userPreferences = UserPreferences(context)
            userPreferences.saveUser(userId, username, email)

            // navigate to Home screen
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Gagal terhubung ke server: ${e.message}", Toast.LENGTH_LONG).show()
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterTextPreview() {
    PblMobileTheme {
        RegisterText() {}
    }
}
