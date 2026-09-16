package com.example.libri

import android.content.Context

class TokenManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "libri_preferences",
        Context.MODE_PRIVATE
    )

    fun saveToken(token: String) {
        preferences.edit()
            .putString("jwt_token", token)
            .apply()
    }

    fun getToken(): String? {
        return preferences.getString("jwt_token", null)
    }

    fun clearToken() {
        preferences.edit()
            .remove("jwt_token")
            .apply()

        clearRole()
    }

    fun saveRole(role: String) {
        preferences.edit()
            .putString("user_role", role)
            .apply()
    }

    fun getRole(): String? {
        return preferences.getString("user_role", null)
    }

    fun clearRole() {
        preferences.edit()
            .remove("user_role")
            .apply()
    }
}