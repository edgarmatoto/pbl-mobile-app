package com.example.pblmobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "Tentang aplikasi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },

        content = { paddingValues ->
            // Membuat konten dapat discroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // Membuat konten scrollable
            ) {
                AboutContent()
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Preview
@Composable
fun AboutScreenPreview() {
    PblMobileTheme {
        AboutScreen(navController = NavController(LocalContext.current))
    }
}

@Composable
fun AboutContent() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(18.dp))

        Text(
            text = "Aplikasi ini memudahkan pengelolaan peternakan atau hewan peliharaan dari smartphone. Dengan fitur pemberian pakan otomatis, pemantauan stok makanan, monitoring suhu dan kelembapan, serta notifikasi keamanan. Solusi lengkap untuk memastikan kesehatan dan kenyamanan hewan Anda secara praktis.",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun AboutContentPreview() {
    AboutContent()
}