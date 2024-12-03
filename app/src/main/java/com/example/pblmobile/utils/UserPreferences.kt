package com.example.pblmobile.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.pblmobile.apiService.user.User

class UserPreferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(id: Int, username: String, email: String) {
        preferences.edit()
            .putInt(KEY_USER_ID, id)
            .putString(KEY_USERNAME, username)
            .putString(KEY_EMAIL, email)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun getUser(): User {
        val id = preferences.getInt(KEY_USER_ID, -1)
        val username = preferences.getString(KEY_USERNAME, "") ?: "-"
        val email = preferences.getString(KEY_EMAIL, "") ?: "-"
        return User(id, username, email)
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearUser() {
        preferences.edit()
            .remove(KEY_USER_ID)
            .remove(KEY_USERNAME)
            .remove(KEY_EMAIL)
            .remove(KEY_IS_LOGGED_IN)
            .apply()
    }
}