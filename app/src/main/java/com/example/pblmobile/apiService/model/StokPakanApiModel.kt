package com.example.pblmobile.apiService.model

data class StokPakanApiResponse(
    val status: Boolean,
    val data: StokPakanData?
)

data class StokPakanData(
    val id: Int,
    val stok: Float,
    val timestamp: String
)