package com.example.pblmobile.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.pblmobile.HomeActivity
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.user.LoginRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.utils.UserPreferences
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PblMobileTheme {
                LoginScreen(context = LocalContext.current)
            }
        }
    }
}

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
fun LoginScreen(modifier: Modifier = Modifier, context: Context) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
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
                        handleLogin(username, password, context)
                    }
                }
            }
        },
        bottomBar = {
            RegisterText {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            }
        }
    )
}

suspend fun handleLogin(username: String, password: String, context: Context) {
    try {
        val loginRequest = LoginRequest(username, password)
        val response = RetrofitInstance.api.loginUser(loginRequest)
        if (response.status) {
            val userId = response.user.id
            val email = response.user.email

            val userPreferences = UserPreferences(context)
            userPreferences.saveUser(userId, username, email)

            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
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
