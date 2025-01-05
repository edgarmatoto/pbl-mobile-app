package com.example.pblmobile.apiService.model

data class AlarmRequest(
    val id: Int? = null,
    val jam_mulai: String,
    val jam_selesai: String,
    val buzzer: String
)

data class AlarmApiResponse(
    val status: Boolean,
    val message: String,
    val data: List<AlarmData>? = null
)

data class AlarmData(
    val id: Int,
    val jam_mulai: String,
    val jam_selesai: String,
    val buzzer: String
)

data class IdAlarmRequest(
    val id: Int,
)
