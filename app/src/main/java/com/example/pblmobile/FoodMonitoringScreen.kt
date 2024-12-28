package com.example.pblmobile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.IdJadwalPakanRequest
import com.example.pblmobile.component.ButtonDetail
import com.example.pblmobile.component.GroupButton
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.viewmodel.jadwalPakan.JadwalPakanViewModel
import com.example.pblmobile.viewmodel.stokPakan.StokPakanViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FoodMonitoringScreen(navController: NavController) {
    val stokPakanViewModel: StokPakanViewModel = viewModel()
    val jadwalPakanViewModel: JadwalPakanViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            stokPakanViewModel.fetchLatestStokPakan()
            jadwalPakanViewModel.fetchJadwalPakan()
            delay(10000) // Refresh setiap 10 detik
        }
    }

    // Collect state from ViewModel
    val stokPakan = stokPakanViewModel.stokPakan.collectAsState().value
    val jadwalPakan = jadwalPakanViewModel.jadwalPakan.collectAsState().value

    // List button
    val buttonModeDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_schedule_24,
            label = "Terjadwal",
            actionButton = {
                FoodScheduleSwitch()
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
                        text = "Monitoring Pakan",
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
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                        shape = ShapeDefaults.ExtraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Jadwal Pakan",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )

                            Column() {
                                jadwalPakan?.forEach { jadwal ->
                                    val jamFormatted = jadwal.jam.substring(0, 5)
                                    val coroutineScope = rememberCoroutineScope()
                                    val context = LocalContext.current

                                    Row {
                                        Text(
                                            text = "$jamFormatted - ${jadwal.gram} gram",
                                        )

                                        IconButton(onClick = {
                                            coroutineScope.launch {
                                                try {
                                                    val deleteRequest = IdJadwalPakanRequest(id = jadwal.id)
                                                    val response = RetrofitInstance.api.deleteJadwalPakan(deleteRequest)

                                                    if (response.status) {
                                                        Toast.makeText(context, "Jadwal berhasil dihapus", Toast.LENGTH_SHORT).show()
                                                        // Perbarui UI dengan memuat ulang data
                                                        jadwalPakanViewModel.fetchJadwalPakan()
                                                    } else {
                                                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                                    }
                                                } catch (e: Exception) {
                                                    Toast.makeText(context, "Gagal menghapus jadwal: ${e.message}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }) {
                                            Icon(Icons.Filled.Delete, contentDescription = "Hapus Jadwal")
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
//                                Button(
//                                    modifier = Modifier,
//                                    shape = ShapeDefaults.Large,
//                                    colors = ButtonDefaults.buttonColors(Color.White),
//                                    onClick = {
//                                        // TODO: Fungsi buka pakan
//                                    }) {
//                                    Text(text = "Buka", color = Color.Black)
//                                }

                                IconButton(
                                    modifier = Modifier,
                                    colors = IconButtonDefaults.iconButtonColors(Color.White),
                                    onClick = {
                                        navController.navigate("addJadwalPakan"){
                                            popUpTo("foodMonitoringScreen") { inclusive = true }
                                        }
                                    }) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_add_24),
                                        contentDescription = null,
                                        tint = Color.Black
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Stok makanan Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            colors = CardDefaults.cardColors(Color(0xFFDBD3D3)),
                            shape = ShapeDefaults.ExtraLarge
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxWidth(), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${stokPakan?.stok?.toInt() ?: "--"}%",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Stok Makanan",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Beri pakan Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            colors = CardDefaults.cardColors(Color(0xFFDBD3D3)),
                            shape = ShapeDefaults.ExtraLarge
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxWidth(), contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.pakan_1),
                                        contentDescription = null,
                                        modifier = Modifier.size(90.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Beri Makan",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )

//                                    IconButton (
//                                        modifier = Modifier,
//                                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
//                                        onClick = {
//                                            // TODO: Beri pakan
//                                        }) {
//                                        Icon(
//                                            painter = painterResource(R.drawable.baseline_start_24),
//                                            contentDescription = null,
//                                            tint = Color.White
//                                        )
//                                    }
                                }
                            }
                        }
                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            // Mode setting
                            Text(
                                modifier = Modifier.padding(horizontal = 18.dp),
                                text = "Mode",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFA8A8A8)
                            )
                            Spacer(modifier = Modifier.size(12.dp))

                            GroupButton(buttonDetails = buttonModeDetails)
                            Spacer(modifier = Modifier.size(12.dp))
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
fun FoodScheduleSwitch() {
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
fun FoodMonitoringScreenPreview() {
    PblMobileTheme {
        FoodMonitoringScreen(NavController((LocalContext.current)))
    }
}