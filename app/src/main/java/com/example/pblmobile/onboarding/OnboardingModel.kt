package com.example.pblmobile.onboarding

import androidx.annotation.DrawableRes
import com.example.pblmobile.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    data object FirstPage: OnboardingModel(
        image = R.drawable.onboarding_1,
        title = "Pemberian Pakan Otomatis",
        description = "Penuhi kebutuhan pakan ayam secara otomatis, tanpa khawatir lagi dengan jadwal pemberian pakan. Semua teratur sesuai kebutuhan ternak."
    )

    data object SecondPage: OnboardingModel(
        image = R.drawable.onboarding_2,
        title = "Pemantauan Stok Makanan",
        description = "Jaga ketersediaan pakan ayam dengan fitur pemantauan stok. Dapatkan notifikasi kapan pun stok mulai menipis sehingga Anda bisa mengisi ulang tepat waktu."
    )
    data object ThirdPage: OnboardingModel(
        image = R.drawable.onboarding_3,
        title = "Monitoring Suhu dan Kelembapan Telur",
        description = "Pastikan kondisi ideal untuk telur dengan memantau suhu dan kelembapan. Semua parameter lingkungan terkendali dengan mudah."
    )
}