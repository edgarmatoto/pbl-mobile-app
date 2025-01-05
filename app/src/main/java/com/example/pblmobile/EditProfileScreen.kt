package com.example.pblmobile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.UpdateProfileRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.apiService.model.User
import com.example.pblmobile.component.PasswordInputField
import com.example.pblmobile.component.StringInputField
import com.example.pblmobile.utils.UserDatastore
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userDatastore = remember { UserDatastore(context) }
    val coroutineScope = rememberCoroutineScope()

    // Collect user data and login status from DataStore
    val userData by userDatastore.user.collectAsState(initial = User(-1, "", ""))
    val isLoggedIn by userDatastore.isLoggedIn.collectAsState(initial = false)

    // State variables for input fields
    var id by remember { mutableStateOf(userData.id) }
    var email by remember { mutableStateOf(userData.email) }
    var username by remember { mutableStateOf(userData.username) }
    var password by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier,
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Profil",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = {
            Column(Modifier.padding(it).padding(horizontal = 10.dp).fillMaxWidth()) {
                StringInputField(data = username, label = "Username", placeholder = username) { username = it }
                Spacer(Modifier.size(5.dp))

                StringInputField (data = email, label = "Email", placeholder = email) { email = it }
                Spacer(Modifier.size(5.dp))

                PasswordInputField { password = it }
                Spacer(Modifier.size(20.dp))

                PrimaryButton(text = "Selesai") {
                    coroutineScope.launch {
                        val updateRequest = UpdateProfileRequest(id, username, email, password)
                        try {
                            val response = RetrofitInstance.api.updateUser(updateRequest)
                            if (response.status) {
                                // memperbarui user preference
                                    userDatastore.clearUser()
                                    userDatastore.saveUser(id, username, email)
                                Toast.makeText(context, "Update berhasil.", Toast.LENGTH_LONG).show()
                                navController.navigate("profile") {
                                    popUpTo("editProfile") { inclusive = true }
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
        bottomBar = { })
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    PblMobileTheme {
        EditProfileScreen(NavController(LocalContext.current))
    }
}