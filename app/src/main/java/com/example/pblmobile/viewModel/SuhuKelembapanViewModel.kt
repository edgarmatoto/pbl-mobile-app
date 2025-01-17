package com.example.pblmobile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.SuhuKelembapanData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SuhuKelembapanViewModel : ViewModel() {
    private val _suhuKelembapan = MutableStateFlow<SuhuKelembapanData?>(null)
    val suhuKelembapan: StateFlow<SuhuKelembapanData?> = _suhuKelembapan

    fun fetchLatestData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getLatestSuhuKelembapan()
                if (response.status) {
                    _suhuKelembapan.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}