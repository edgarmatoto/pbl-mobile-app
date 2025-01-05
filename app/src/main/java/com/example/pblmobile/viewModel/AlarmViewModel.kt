package com.example.pblmobile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.AlarmData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlarmViewModel : ViewModel() {
    // Menggunakan StateFlow untuk menyimpan daftar jadwal pakan
    private val _alarm = MutableStateFlow<List<AlarmData>>(emptyList())
    val alarm: StateFlow<List<AlarmData>?> = _alarm

    // Mengambil data jadwal pakan terbaru
    fun fetchAlarm() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAlarm()

                if (response.status && response.data != null) {
                    _alarm.value = response.data  // Mengisi StateFlow dengan daftar jadwal pakan
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}