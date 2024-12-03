package com.example.pblmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pblmobile.authentication.LoginScreen
import com.example.pblmobile.authentication.RegisterScreen
import com.example.pblmobile.onboarding.OnboardingScreen
import com.example.pblmobile.onboarding.OnboardingUtils
import com.example.pblmobile.ui.theme.PblMobileTheme

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

    NavHost(
        navController = navController,
        startDestination = if (OnboardingUtils(context).isOnboardCompleted()) "login" else "onboarding"
    ) {
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
        composable("eggMonitoring") {
            EggMonitoringScreen(navController)
        }
        composable("foodMonitoring") {
            FoodMonitoringScreen(navController)
        }
        composable("securityAlarm") {
            SecurityAlarmScreen(navController)
        }
    }
}