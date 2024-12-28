package com.example.pblmobile.viewmodel.jadwalPakan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.JadwalPakanData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JadwalPakanViewModel : ViewModel() {
    // Menggunakan StateFlow untuk menyimpan daftar jadwal pakan
    private val _jadwalPakan = MutableStateFlow<List<JadwalPakanData>>(emptyList())
    val jadwalPakan: StateFlow<List<JadwalPakanData>?> = _jadwalPakan

    // Mengambil data jadwal pakan terbaru
    fun fetchJadwalPakan() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getJadwalPakan()

                if (response.status && response.data != null) {
                    _jadwalPakan.value = response.data  // Mengisi StateFlow dengan daftar jadwal pakan
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
