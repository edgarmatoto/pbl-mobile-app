package com.example.pblmobile.apiService.model

data class SuhuKelembapanApiResponse(
    val status: Boolean,
    val data: SuhuKelembapanData
)

data class SuhuKelembapanData(
    val id: Int,
    val suhu: String,
    val kelembapan: String,
    val timestamp: String
)
