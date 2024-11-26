package com.example.tugas13

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class PrefManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(
        FILE_NAME, Context.MODE_PRIVATE
    )

    companion object {
        private val FILE_NAME = "SharedPreferencesApp"
        private val KEY_USERNAME = "username"
        private val KEY_PASSWORD = "password"
        private val KEY_IS_LOGGED_IN = "isLoggedIn"

        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun setUsername(username: String) {
        pref.edit().apply {
            putString(KEY_USERNAME, username)
            commit()
        }
    }

    fun getUsername(): String? {
        return pref.getString(KEY_USERNAME, null)
    }

    fun setPassword(password: String) {
        pref.edit().apply {
            putString(KEY_PASSWORD, password)
            commit()
        }
    }

    fun getPassword(): String? {
        return pref.getString(KEY_PASSWORD, null)
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        pref.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            commit()
        }
    }

    fun getIsLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clear() {
        pref.edit().apply {
            clear()
            commit()
        }
    }
}