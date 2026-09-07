package com.example.libri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.libri.TokenManager
import com.example.libri.data.api.RetrofitClient
import com.example.libri.data.model.User
import com.example.libri.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)

    private val repository = AuthRepository(
        RetrofitClient.api,
        tokenManager
    )

    private val _loginResult = MutableStateFlow<User?>(null)
    val loginResult: StateFlow<User?> = _loginResult

    private val _registerResult = MutableStateFlow<User?>(null)
    val registerResult: StateFlow<User?> = _registerResult

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val response = repository.login(email, password)

                // Simpan JWT
                tokenManager.saveToken(response.token)

                // Simpan user hasil login
                _loginResult.value = response.user

            } catch (e: Exception) {
                _error.value = e.message ?: "Login failed"
            } finally {
                _loading.value = false
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val response = repository.register(
                    name = name,
                    email = email,
                    password = password
                )

                _registerResult.value = response.user

            } catch (e: Exception) {
                _error.value = e.message ?: "Registration failed"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}