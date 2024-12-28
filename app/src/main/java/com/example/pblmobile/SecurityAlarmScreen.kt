package com.example.pblmobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.component.ButtonDetail
import com.example.pblmobile.component.GroupButton
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun SecurityAlarmScreen(navController: NavController) {
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

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 10.dp), contentAlignment = Alignment.CenterStart
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
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                        shape = ShapeDefaults.ExtraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Jadwal Alarm",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )

                            Column() {
                                Text(text = "08.00 - 40 gram")
                                Text(text = "08.00 - 40 gram")
                                Text(text = "08.00 - 40 gram")
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
                                        // TODO: tambah jadwal pakan
                                    }
                                ) {
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
                            .height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Alarm card
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
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.baseline_hearing_24),
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
                                        text = "Alarm",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    IconButton(
                                        modifier = Modifier,
                                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
                                        onClick = {
                                            // TODO: Beri pakan
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_play_arrow_24),
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Box(modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f))
                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp))

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

@Preview
@Composable
fun SecurityAlarmScreenPreview() {
    PblMobileTheme {
        SecurityAlarmScreen(NavController((LocalContext.current)))
    }
}