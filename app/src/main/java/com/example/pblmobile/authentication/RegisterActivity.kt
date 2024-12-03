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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.user.RegisterRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PblMobileTheme {
                RegisterScreen(context = LocalContext.current)
            }
        }
    }

    @Composable
    private fun RegisterScreen(modifier: Modifier = Modifier, context: Context) {
        // State variables for input fields
        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Scaffold(modifier = modifier,
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
                                    context.startActivity(Intent(context, LoginActivity::class.java))
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
                        startActivity(Intent(context, LoginActivity::class.java))
                    }
                }
            })
    }
}

@Composable
fun EmailInputField(onValueChange: (String) -> Unit) {
    var email by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(text = "Email") },
        placeholder = { Text(text = "prabowo@gmail.com") },
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = {
            email = it
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
