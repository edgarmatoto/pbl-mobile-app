package com.example.pblmobile.apiService

import com.example.pblmobile.apiService.model.IdJadwalPakanRequest
import com.example.pblmobile.apiService.model.JadwalPakanApiResponse
import com.example.pblmobile.apiService.model.JadwalPakanRequest
import com.example.pblmobile.apiService.model.LoginRequest
import com.example.pblmobile.apiService.model.RegisterRequest
import com.example.pblmobile.apiService.model.StokPakanApiResponse
import com.example.pblmobile.apiService.model.UpdateProfileRequest
import com.example.pblmobile.apiService.model.SuhuKelembapanApiResponse
import com.example.pblmobile.apiService.model.UserApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("api/kelompok_2/register_user.php")
    suspend fun registerUser(@Body request: RegisterRequest): UserApiResponse

    @POST("api/kelompok_2/login_user.php")
    suspend fun loginUser(@Body request: LoginRequest): UserApiResponse

    @POST("api/kelompok_2/update_user.php")
    suspend fun updateUser(@Body request: UpdateProfileRequest): UserApiResponse

    @GET("api/kelompok_2/suhu_kelembapan.php")
    suspend fun getLatestSuhuKelembapan(): SuhuKelembapanApiResponse

    @GET("api/kelompok_2/stok_pakan.php")
    suspend fun getLatestStokPakan(): StokPakanApiResponse

    @POST("api/kelompok_2/jadwal_pakan.php")
    suspend fun addJadwalPakan(@Body request: JadwalPakanRequest): JadwalPakanApiResponse

    @GET("api/kelompok_2/jadwal_pakan.php")
    suspend fun getJadwalPakan(): JadwalPakanApiResponse

    @HTTP(method = "DELETE", path = "api/kelompok_2/jadwal_pakan.php", hasBody = true)
    suspend fun deleteJadwalPakan(@Body request: IdJadwalPakanRequest): JadwalPakanApiResponse

    @PUT("api/kelompok_2/jadwal_pakan.php")
    suspend fun updateJadwalPakan(@Body request: JadwalPakanRequest): JadwalPakanApiResponse
}