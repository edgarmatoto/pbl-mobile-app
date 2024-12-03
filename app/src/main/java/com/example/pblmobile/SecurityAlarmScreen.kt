package com.example.pblmobile

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
import androidx.navigation.NavController
import com.example.pblmobile.navbar.Navigation

@Composable
fun SecurityAlarmScreen(navController: NavController) {
    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 10.dp), contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Alarm Keamanan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    },

        content = {
            Column(Modifier.padding(it)) {
                Image(
                    painter = painterResource(R.drawable.security_alarm_screen),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Preview
@Composable
fun SecurityAlarmScreenPreview() {
    SecurityAlarmScreen(NavController((LocalContext.current)))
}