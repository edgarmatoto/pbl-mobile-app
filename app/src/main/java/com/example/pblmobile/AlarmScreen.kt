package com.example.pblmobile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.IdAlarmRequest
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.viewModel.AlarmViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlarmScreen(navController: NavController) {
    val alarmViewModel: AlarmViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            alarmViewModel.fetchAlarm()
            delay(10000) // Refresh setiap 10 detik
        }
    }

    // Collect state from ViewModel
    val alarmData = alarmViewModel.alarm.collectAsState().value

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
                        text = "Alarm Buzzer",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(Modifier.padding(12.dp)) {
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                        shape = ShapeDefaults.ExtraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Jadwal Alarm",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )

                            Column() {
                                alarmData?.forEachIndexed { index, alarm ->
                                    val jamMulaiFormatted = alarm.jam_mulai.substring(0, 5)
                                    val jamSelesaiFormatted = alarm.jam_selesai.substring(0, 5)
                                    val buzzer = alarm.buzzer

                                    val coroutineScope = rememberCoroutineScope()
                                    val context = LocalContext.current

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "$jamMulaiFormatted - ${jamSelesaiFormatted} (${buzzer} detik berbunyi)",
                                        )

                                        IconButton(onClick = {
                                            coroutineScope.launch {
                                                try {
                                                    val deleteRequest = IdAlarmRequest(id = alarm.id)
                                                    val response = RetrofitInstance.api.deleteAlarm(deleteRequest)

                                                    if (response.status) {
                                                        Toast.makeText(
                                                            context, "Alarm berhasil dihapus", Toast.LENGTH_SHORT
                                                        ).show()
                                                        // Perbarui UI dengan memuat ulang data
                                                        alarmViewModel.fetchAlarm()
                                                    } else {
                                                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT)
                                                            .show()
                                                    }
                                                } catch (e: Exception) {
                                                    Toast.makeText(
                                                        context,
                                                        "Gagal menghapus alarm: ${e.message}",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        }) {
                                            Icon(
                                                Icons.Filled.Delete,
                                                contentDescription = "Hapus Alarm",
                                                tint = Color.Red
                                            )
                                        }
                                    }

                                    if (index < (alarmData.size - 1)) {
                                        Divider(
                                            thickness = 1.dp,
                                            color = Color.LightGray,
                                            modifier = Modifier.padding(vertical = 5.dp)
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    modifier = Modifier,
                                    colors = IconButtonDefaults.iconButtonColors(Color.White),
                                    onClick = {
                                        navController.navigate("addAlarm") {
                                            popUpTo("alarm") { inclusive = true }
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
                        // Alarm card
//                        Card(
//                            modifier = Modifier
//                                .weight(1f)
//                                .aspectRatio(1f),
//                            colors = CardDefaults.cardColors(Color(0xFFDBD3D3)),
//                            shape = ShapeDefaults.ExtraLarge
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(12.dp)
//                                    .fillMaxSize(),
//                                verticalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .weight(2f)
//                                        .fillMaxWidth(), contentAlignment = Alignment.Center
//                                ) {
//                                    Image(
//                                        painter = painterResource(R.drawable.baseline_hearing_24),
//                                        contentDescription = null,
//                                        modifier = Modifier.size(90.dp)
//                                    )
//                                }
//
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .weight(1f),
//                                    horizontalArrangement = Arrangement.Center,
//                                    verticalAlignment = Alignment.CenterVertically,
//                                ) {
//                                    Text(
//                                        text = "Alarm",
//                                        style = MaterialTheme.typography.titleMedium,
//                                        fontWeight = FontWeight.Bold
//                                    )
//
////                                    IconButton(
////                                        modifier = Modifier,
////                                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
////                                        onClick = {
////                                            // TODO: bunyikan alarm buzzer
////                                        }) {
////                                        Icon(
////                                            painter = painterResource(R.drawable.baseline_play_arrow_24),
////                                            contentDescription = null,
////                                            tint = Color.White
////                                        )
////                                    }
//                                }
//                            }
//                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )
                }
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Preview
@Composable
fun AlarmScreenPreview() {
    PblMobileTheme {
        AlarmScreen(NavController((LocalContext.current)))
    }
}