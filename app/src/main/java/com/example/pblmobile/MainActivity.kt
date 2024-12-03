package com.example.pblmobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.pblmobile.authentication.LoginActivity
import com.example.pblmobile.onboarding.OnboardingScreen
import com.example.pblmobile.onboarding.OnboardingUtils
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PblMobileTheme {
                // A surface container using the 'background' color from the theme
                if (onboardingUtils.isOnboardCompleted()) {
                    LaunchedEffect(Unit) {
                        navigateToLogin()
                    }
                } else {
                    showOnboarding()
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    @Composable
    private fun showOnboarding() {
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        OnboardingScreen {
            onboardingUtils.setOnboardComplete()

            coroutineScope.launch {
                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as? MainActivity)?.finish()
            }
        }
    }
}