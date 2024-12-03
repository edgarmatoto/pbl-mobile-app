package com.example.pblmobile.apiService.user

import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("api/kelompok_4/register_user.php")
    suspend fun registerUser(@Body request: RegisterRequest): ApiResponse

    @POST("api/kelompok_4/login_user.php")
    suspend fun loginUser(@Body request: LoginRequest): ApiResponse
}