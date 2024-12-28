package com.example.pblmobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pblmobile.component.ButtonDetail
import com.example.pblmobile.component.GroupButton
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.viewModel.suhuKelembapan.SuhuKelembapanViewModel
import kotlinx.coroutines.delay

@Composable
fun EggMonitoringScreen(navController: NavController) {
    val viewModel: SuhuKelembapanViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.fetchLatestData()
            delay(10000) // Refresh setiap 10 detik
        }
    }

    // Collect state from ViewModel
    val suhuKelembapan = viewModel.suhuKelembapan.collectAsState().value

    // List mode button
    val buttonModeDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_auto_mode_24,
            label = "Otomatis",
            actionButton = {
                AutomaticSwitch()
            },
        )
    )

    // List button untuk bagian preferensi
    val buttonPreferensiDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_device_thermostat_24,
            label = "Suhu & Kelembapan",
            actionButton = {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Edit")
                }
            },
        ),
//        ButtonDetail(
//            icon = R.drawable.baseline_timer_24,
//            label = "Timer Rak Telur",
//            actionButton = {
//                TextButton(onClick = { /*TODO*/ }) {
//                    Text(text = "Edit")
//                }
//            },
//        ),
//        ButtonDetail(
//            icon = R.drawable.baseline_rotate_right_24,
//            label = "Ubah Posisi Telur",
//            actionButton = {
//                TextButton(onClick = { /*TODO*/ }) {
//                    Text(text = "Rotasi")
//                }
//            },
//        ),
    )

    val buttonAlatDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_lightbulb_outline_24,
            label = "Lampu",
            actionButton = {
                AutomaticSwitch()
            },
        ), ButtonDetail(
            icon = R.drawable.mode_fan_24dp,
            label = "Kipas",
            actionButton = {
                AutomaticSwitch()
            },
        )
    )

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
                        text = "Monitoring Kandang",
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
                Column(Modifier.padding(12.dp)) {
                    // Egg Monitoring Card
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                        shape = ShapeDefaults.ExtraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 20.dp, horizontal = 28.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Suhu & Kelembapan Kandang",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column {
                                    Text(
                                        text = "${suhuKelembapan?.suhu ?: "--"}° C",
                                        style = MaterialTheme.typography.displayLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "${suhuKelembapan?.kelembapan ?: "--"}%",
                                        style = MaterialTheme.typography.displayLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }

                                IconButton(
                                    modifier = Modifier,
                                    colors = IconButtonDefaults.iconButtonColors(Color.Black),
                                    onClick = {
                                        viewModel.fetchLatestData()
                                    }) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_refresh_24),
                                        contentDescription = null,
                                        tint = Color.White,

                                        )
                                }
                            }
                        }
                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Column {
                            // Mode setting
                            Text(
                                modifier = Modifier.padding(horizontal = 18.dp),
                                text = "Mode",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFA8A8A8)
                            )
                            Spacer(modifier = Modifier.size(8.dp))

                            GroupButton(buttonDetails = buttonModeDetails)
                            Spacer(modifier = Modifier.size(12.dp))

                            // Preferensi setting
                            Text(
                                modifier = Modifier.padding(horizontal = 18.dp),
                                text = "Preferensi",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFA8A8A8)
                            )
                            Spacer(modifier = Modifier.size(12.dp))

                            GroupButton(buttonDetails = buttonPreferensiDetails)
                            Spacer(modifier = Modifier.size(12.dp))

                            // Alat setting
                            Text(
                                modifier = Modifier.padding(horizontal = 18.dp),
                                text = "Alat",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFA8A8A8)
                            )
                            Spacer(modifier = Modifier.size(12.dp))

                            GroupButton(buttonDetails = buttonAlatDetails)
                        }
                    }
                }
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Composable
fun AutomaticSwitch() {
    var isChecked by remember { mutableStateOf(false) }

    Switch(
        checked = isChecked, onCheckedChange = { isChecked = it }, colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White, // Warna lingkaran saat aktif
            checkedTrackColor = Color(0xFFFF9800), // Warna latar belakang saat aktif
            uncheckedThumbColor = Color.White, // Warna lingkaran saat tidak aktif
            uncheckedTrackColor = Color.Gray // Warna latar belakang saat tidak aktif
        )
    )
}

@Preview
@Composable
fun EggMonitoringScreenPreview() {
    PblMobileTheme {
        EggMonitoringScreen(NavController(LocalContext.current))
    }
}