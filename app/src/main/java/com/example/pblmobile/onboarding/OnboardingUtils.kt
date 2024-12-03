package com.example.pblmobile.onboarding

import android.content.Context

class OnboardingUtils(private val context: Context) {
    fun isOnboardCompleted(): Boolean {
            return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
                .getBoolean("completed", false)
    }

    fun setOnboardComplete() {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)
            .apply()
    }
}