package com.example.pblmobile

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.component.ButtonDetail
import com.example.pblmobile.component.GroupButton
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.apiService.model.User
import com.example.pblmobile.utils.UserDatastore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Profil", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )
            }
        }
    },

        content = { paddingValues ->
            // Membuat konten dapat discroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // Membuat konten scrollable
            ) {
                ProfileContent(navController)
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Preview
@Composable
fun ProfileScreenPreview() {
    PblMobileTheme {
        ProfileScreen(NavController(LocalContext.current))
    }
}

@Composable
fun ProfileContent(navController: NavController) {
    val context = LocalContext.current
    val userDatastore = UserDatastore(context)
    val userState = remember { mutableStateOf(User(-1, "", "")) }
    val isLoggedInState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Collect user data from UserDatastore
    LaunchedEffect(Unit) {
        userDatastore.user.collect { user ->
            userState.value = user
        }
        userDatastore.isLoggedIn.collect { status ->
            isLoggedInState.value = status
        }
    }

    // ambil preference navbar untuk di reset ketika logout
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // List button untuk bagian keamanan
    val buttonKeamananDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_lock_outline_24,
            label = "Ganti password",
            actionButton = {
                IconButton(onClick = {
                    navController.navigate("editProfile")
                }) {
                    Image(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            },
        )
    )

    // List button untuk bagian preferensi
    val buttonPreferensiDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_notifications_none_24,
            label = "Nyalakan notifikasi",
            actionButton = {
                NotificationSwitch()
            },
        ),
        ButtonDetail(
            icon = R.drawable.baseline_logout_24,
            label = "Logout",
            actionButton = {
                IconButton(onClick = {
                    scope.launch {
                        userDatastore.clearUser()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }

                        (context as Activity).finishAffinity()
                        val restartIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                        restartIntent?.let { context.startActivity(it) }
                    }
                }) {
                    Image(
                        imageVector = Icons.Default.ArrowForward,
                        colorFilter = ColorFilter.tint(Color.Red),
                        contentDescription = null
                    )
                }
            },
            color = Color.Red
        )
    )

    Column(Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.prabowo),
            contentDescription = null,
            modifier = Modifier.size(120.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.size(18.dp))

        Text(
            text = userState.value.username,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = userState.value.email,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(14.dp))

        PrimaryButton(modifier = Modifier.width(120.dp), text = "Edit profil", shape = ShapeDefaults.ExtraLarge) {
            navController.navigate("editProfile")
        }
        Spacer(modifier = Modifier.size(18.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                // Keamanan setting
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = "Keamanan",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA8A8A8)
                )
                Spacer(modifier = Modifier.size(12.dp))

                GroupButton(buttonDetails = buttonKeamananDetails)
                Spacer(modifier = Modifier.size(12.dp))

                // Preferensi setting
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = "Preferensi",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA8A8A8)
                )
                Spacer(modifier = Modifier.size(12.dp))

                GroupButton(buttonDetails = buttonPreferensiDetails)
            }
        }
    }
}

@Composable
fun NotificationSwitch() {
    var isChecked by remember { mutableStateOf(true) }

    Switch(
        checked = isChecked,
        onCheckedChange = { isChecked = it },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White, // Warna lingkaran saat aktif
            checkedTrackColor = Color(0xFFFF9800), // Warna latar belakang saat aktif
            uncheckedThumbColor = Color.White, // Warna lingkaran saat tidak aktif
            uncheckedTrackColor = Color.Gray // Warna latar belakang saat tidak aktif
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    PblMobileTheme {
        ProfileContent(NavController(LocalContext.current))
    }
}