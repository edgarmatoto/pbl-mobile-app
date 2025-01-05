package com.example.pblmobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.viewModel.suhuKelembapan.SuhuKelembapanViewModel
import com.example.pblmobile.apiService.model.User
import com.example.pblmobile.utils.UserDatastore
import com.example.pblmobile.viewmodel.jadwalPakan.JadwalPakanViewModel
import com.example.pblmobile.viewmodel.stokPakan.StokPakanViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val userDatastore = UserDatastore(context)
    val userState = remember { mutableStateOf(User(-1, "", "")) }

    // Collect user data from UserDatastore
    LaunchedEffect(Unit) {
        userDatastore.user.collect { user ->
            userState.value = user
        }
    }

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
                        text = "Selamat Datang,",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = userState.value.username.ifEmpty { "Unknown" },
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
                HomeContent(navController)
            }
        },

        bottomBar = {
            Navigation(navController = navController)
        })
}

@Preview
@Composable
fun HomeScreenPreview() {
    PblMobileTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val suhuKelembapanViewModel: SuhuKelembapanViewModel = viewModel()
    val stokPakanViewModel: StokPakanViewModel = viewModel()
    val jadwalPakanViewModel: JadwalPakanViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            suhuKelembapanViewModel.fetchLatestData()
            stokPakanViewModel.fetchLatestStokPakan()
            jadwalPakanViewModel.fetchJadwalPakan()
            delay(10000)
        }
    }

    val jadwalPakan = jadwalPakanViewModel.jadwalPakan.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar Card
        ) {
            // Food Monitoring Card (Jadwal)
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        navController.navigate("foodMonitoring") {
                            popUpTo("foodMonitoring") { inclusive = true }
                        }
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Jadwal Pakan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold
                    )

                    Column() {
                        jadwalPakan?.forEach { jadwal ->
                            val jamFormatted = jadwal.jam.substring(0, 5)
                            Text(
                                text = "$jamFormatted - ${jadwal.detik} detik",
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier,
                            shape = ShapeDefaults.Large,
                            colors = ButtonDefaults.buttonColors(Color.White),
                            onClick = {
                                navController.navigate("foodMonitoring") {
                                    popUpTo("foodMonitoring") { inclusive = true }
                                }
                            }) {
                            Text(text = "Buka", color = Color.Black)
                        }

                        IconButton(
                            modifier = Modifier, colors = IconButtonDefaults.iconButtonColors(Color.White), onClick = {
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

            // Food Monitoring Card (Stok)
            StokPakanCard(
                Modifier
                    .weight(1f)
                    .aspectRatio(1f), navController, stokPakanViewModel
            )
        }

        // Egg Monitoring Card
        SuhuKelembapanCard(navController, suhuKelembapanViewModel)

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar Card
        ) {

            // Coming soon Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        navController.navigate("securityAlarm") {
                            popUpTo("securityAlarm") { inclusive = true }
                        }
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Coming Soon",
                    )
                }
            }

            // Coming soon Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        // Coming soon activity here
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Coming Soon",
                    )
                }
            }
        }
    }
}

@Composable
fun SuhuKelembapanCard(
    navController: NavController, viewModel: SuhuKelembapanViewModel = viewModel()
) {
    // Collect state from ViewModel
    val suhuKelembapan = viewModel.suhuKelembapan.collectAsState().value

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .clickable {
//                navController.navigate("eggMonitoring") {
//                    popUpTo("foodMonitoring") { inclusive = true }
//                }
            }, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary), shape = ShapeDefaults.ExtraLarge
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 28.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Suhu & Kelembapan Kandang", style = MaterialTheme.typography.titleLarge, color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                    modifier = Modifier, colors = IconButtonDefaults.iconButtonColors(Color.Black), onClick = {
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
}

@Composable
fun StokPakanCard(modifier: Modifier, navController: NavController, viewModel: StokPakanViewModel = viewModel()) {
    // Collect state from ViewModel
    val stokPakan = viewModel.stokPakan.collectAsState().value

    Card(
        modifier = modifier.clickable {
//            navController.navigate("foodMonitoring") {
//                popUpTo("foodMonitoring") { inclusive = true }
//            }
        }, colors = CardDefaults.cardColors(Color(0xFFDBD3D3)), shape = ShapeDefaults.ExtraLarge
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Stok Makanan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}