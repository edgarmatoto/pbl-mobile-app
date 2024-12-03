package com.example.pblmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme

class EggMonitoringActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PblMobileTheme {
                EggMonitoringScreen()
            }
        }
    }
}

@Composable
fun EggMonitoringScreen() {
    val context = LocalContext.current

    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 10.dp), contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Monitoring Telur",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    },

        content = {
            Column(Modifier.padding(it)) {
                Image(
                    painter = painterResource(R.drawable.egg_monitoring_screen),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),  // Agar gambar memenuhi seluruh layar
                    contentScale = ContentScale.Crop
                )
            }
        },

        bottomBar = {
            Navigation()
        })
}

@Preview
@Composable
fun EggMonitoringScreenPreview() {
    EggMonitoringScreen()
}