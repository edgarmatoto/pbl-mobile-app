package com.example.pblmobile.authentication

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.RegisterRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    // State variables for input fields
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier,
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar akun",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = {
            Column(Modifier.padding(it).padding(horizontal = 10.dp).fillMaxWidth()) {
                UsernameInputField { username = it }
                Spacer(Modifier.size(5.dp))

                EmailInputField { email = it }
                Spacer(Modifier.size(5.dp))

                PasswordInputField { password = it }
                Spacer(Modifier.size(20.dp))

                val coroutineScope = rememberCoroutineScope()
                PrimaryButton(text = "Daftar") {
                    coroutineScope.launch {
                        val registerRequest = RegisterRequest(username, email, password)
                        try {
                            val response = RetrofitInstance.api.registerUser(registerRequest)
                            if (response.status) {
                                // Berhasil register, navigasi ke LoginActivity
                                Toast.makeText(context, "Registrasi berhasil. Silakan login.", Toast.LENGTH_LONG).show()
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                // Tampilkan error dari response
                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Gagal terhubung ke server: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        },
        bottomBar = {
            val coroutineScope = rememberCoroutineScope()

            LoginText {
                coroutineScope.launch {
                    navController.navigate("login")
                }
            }
        })
}

@Composable
fun EmailInputField(email: String = "", onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(email) }

    OutlinedTextField(
        label = { Text(text = "Email") },
        placeholder = { Text(text = "prabowo@gmail.com") },
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)  // Update the state in the parent composable
        }
    )
}

@Composable
fun LoginText(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier.fillMaxWidth().padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Sudah punya akun? ")

        Text(
            text = "Login", color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                onClick()
            }, textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginTextPreview() {
    PblMobileTheme {
        LoginText { }
    }
}
