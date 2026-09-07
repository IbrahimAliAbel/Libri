package com.example.libri.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val created_at: String? = null
)

data class RegisterResponse(
    val message: String,
    val user: User
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: User
)

data class MeResponse(
    val user: User
)

data class ErrorResponse(
    val message: String
)