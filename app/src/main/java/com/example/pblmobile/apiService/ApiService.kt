package com.example.pblmobile.apiService

import com.example.pblmobile.apiService.model.AlarmApiResponse
import com.example.pblmobile.apiService.model.AlarmRequest
import com.example.pblmobile.apiService.model.IdAlarmRequest
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
    // User API
    @POST("api/kelompok_2/register_user.php")
    suspend fun registerUser(@Body request: RegisterRequest): UserApiResponse

    @POST("api/kelompok_2/login_user.php")
    suspend fun loginUser(@Body request: LoginRequest): UserApiResponse

    @POST("api/kelompok_2/update_user.php")
    suspend fun updateUser(@Body request: UpdateProfileRequest): UserApiResponse

    // Suhu Kelembapan API
    @GET("api/kelompok_2/suhu_kelembapan.php")
    suspend fun getLatestSuhuKelembapan(): SuhuKelembapanApiResponse

    // Stok Pakan API
    @GET("api/kelompok_2/stok_pakan.php")
    suspend fun getLatestStokPakan(): StokPakanApiResponse

    // Jadwal pakan API
    @POST("api/kelompok_2/test-jadwal_pakan.php")
    suspend fun addJadwalPakan(@Body request: JadwalPakanRequest): JadwalPakanApiResponse

    @GET("api/kelompok_2/test-jadwal_pakan.php")
    suspend fun getJadwalPakan(): JadwalPakanApiResponse

    @HTTP(method = "DELETE", path = "api/kelompok_2/test-jadwal_pakan.php", hasBody = true)
    suspend fun deleteJadwalPakan(@Body request: IdJadwalPakanRequest): JadwalPakanApiResponse

    // Alarm API
    @POST("api/kelompok_2/alarm.php")
    suspend fun addAlarm(@Body request: AlarmRequest): AlarmApiResponse

    @GET("api/kelompok_2/alarm.php")
    suspend fun getAlarm(): AlarmApiResponse

    @HTTP(method = "DELETE", path = "api/kelompok_2/alarm.php", hasBody = true)
    suspend fun deleteAlarm(@Body request: IdAlarmRequest): AlarmApiResponse
}