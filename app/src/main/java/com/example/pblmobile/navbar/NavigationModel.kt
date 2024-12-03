package com.example.pblmobile.navbar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    var selectedItem = mutableStateOf(0)
}
