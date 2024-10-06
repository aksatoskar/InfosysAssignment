package com.example.infosysassignment.common.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    fun saveUsername(username: String) {
        sharedPreferences.edit().putString(PreferencesKeys.USERNAME_KEY, username).apply()
    }

    fun saveUserId(id: Int) {
        sharedPreferences.edit().putInt(PreferencesKeys.USERNAME_ID, id).apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(PreferencesKeys.USERNAME_KEY, "")
    }

    fun getUserId(): Int? {
        return sharedPreferences.getInt(PreferencesKeys.USERNAME_ID, 0)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}