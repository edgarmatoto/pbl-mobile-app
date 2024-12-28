package com.example.pblmobile.viewmodel.stokPakan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.StokPakanData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StokPakanViewModel : ViewModel() {
    private val _stokPakan = MutableStateFlow<StokPakanData?>(null)
    val stokPakan: StateFlow<StokPakanData?> = _stokPakan

    // Mengambil data stok pakan terbaru
    fun fetchLatestStokPakan() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getLatestStokPakan()
                if (response.status) {
                    _stokPakan.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
