package com.example.pblmobile.apiService.user

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class ApiResponse(
    val status: Boolean,
    val message: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String,
    val email: String
)