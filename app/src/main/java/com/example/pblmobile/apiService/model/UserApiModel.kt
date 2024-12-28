package com.example.pblmobile.apiService.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class UserApiResponse(
    val status: Boolean,
    val message: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String,
    val email: String
)

data class UpdateProfileRequest(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
)