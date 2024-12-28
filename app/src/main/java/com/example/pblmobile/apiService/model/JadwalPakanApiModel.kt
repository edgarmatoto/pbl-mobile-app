package com.example.pblmobile.apiService.model

data class JadwalPakanRequest(
    val id: Int? = null,
    val jam: String,
    val gram: String
)

data class JadwalPakanApiResponse(
    val status: Boolean,
    val message: String,
    val data: List<JadwalPakanData>? = null
)

data class JadwalPakanData(
    val id: Int,
    val jam: String,
    val gram: String
)

data class IdJadwalPakanRequest(
    val id: Int,
)
