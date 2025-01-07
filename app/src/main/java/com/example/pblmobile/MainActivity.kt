package com.example.pblmobile

import AddJadwalPakanScreen
import AddAlarmScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pblmobile.authentication.LoginScreen
import com.example.pblmobile.authentication.RegisterScreen
import com.example.pblmobile.onboarding.OnboardingScreen
import com.example.pblmobile.onboarding.OnboardingUtils
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.utils.UserDatastore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PblMobileTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userDatastore = UserDatastore(context)
    val isLoggedIn = userDatastore.isLoggedIn.collectAsState(initial = false).value
    val coroutineScope = rememberCoroutineScope()

    var startDestination = "splash"

    // Reset preference navbar
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt("selected_item", 0).apply()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen {
                coroutineScope.launch {
                    delay(2000) // Delay 2 detik untuk splash
                    startDestination = if (isLoggedIn) {
                        "home"
                    } else if (OnboardingUtils(context).isOnboardCompleted()) {
                        "login"
                    } else {
                        "onboarding"
                    }
                    navController.navigate(startDestination) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
        composable("onboarding") {
            OnboardingScreen(onFinished = {
                OnboardingUtils(context).setOnboardComplete()
                navController.navigate("login") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("about") {
            AboutScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("temperature") {
            TemperatureScreen(navController)
        }
        composable("pakan") {
            PakanScreen(navController)
        }
        composable("alarm") {
            AlarmScreen(navController)
        }
        composable("editProfile") {
            EditProfileScreen(navController)
        }
        composable("addJadwalPakan") {
            AddJadwalPakanScreen(navController)
        }
        composable("addAlarm") {
            AddAlarmScreen(navController)
        }
    }
}