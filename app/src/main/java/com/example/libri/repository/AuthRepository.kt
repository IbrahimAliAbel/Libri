package com.example.libri.repository

import com.example.libri.TokenManager
import com.example.libri.data.api.ApiService
import com.example.libri.data.model.LoginRequest
import com.example.libri.data.model.LoginResponse
import com.example.libri.data.model.MeResponse
import com.example.libri.data.model.RegisterRequest
import com.example.libri.data.model.RegisterResponse

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {
        return apiService.register(
            RegisterRequest(
                name = name,
                email = email,
                password = password
            )
        )
    }

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {
        return apiService.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }

    suspend fun getMe(): MeResponse {
        val token = tokenManager.getToken()
            ?: throw Exception("Token not found")

        return apiService.getMe("Bearer $token")
    }

    fun logout() {
        tokenManager.clearToken()
    }
}