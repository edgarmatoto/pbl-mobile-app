package com.example.pblmobile.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pblmobile.apiService.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserDatastore(private val context: Context) {
    companion object {
        private val KEY_USER_ID = intPreferencesKey("user_id")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveUser(id: Int, username: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = id
            preferences[KEY_USERNAME] = username
            preferences[KEY_EMAIL] = email
            preferences[KEY_IS_LOGGED_IN] = true
        }
    }

    val user: Flow<User> = context.dataStore.data.map { preferences ->
        val id = preferences[KEY_USER_ID] ?: -1
        val username = preferences[KEY_USERNAME] ?: ""
        val email = preferences[KEY_EMAIL] ?: ""
        User(id, username, email)
    }

    // Mengecek apakah user sedang login
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] == true
    }

    // Menghapus data user
    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USERNAME)
            preferences.remove(KEY_EMAIL)
            preferences[KEY_IS_LOGGED_IN] = false
        }
    }
}