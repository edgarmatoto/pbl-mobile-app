package com.example.pblmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.ui.theme.PblMobileTheme
import com.example.pblmobile.utils.UserPreferences

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PblMobileTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // Ambil username dari intent
    val userPreferences = UserPreferences(context).getUser()

    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Selamat Datang,",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = userPreferences.username, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )
            }
        }
    },

        content = {
            Column(Modifier.padding(it)) {
                HomeContent()
            }
        },

        bottomBar = {
            com.example.pblmobile.navbar.Navigation(modifier)
        })
}

@Preview
@Composable
fun HomeScreenPreview() {
    PblMobileTheme {
        HomeScreen()
    }
}

@Composable
fun HomeContent() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar Card
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        context.startActivity(Intent(context, FoodMonitoringActivity::class.java))
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Jadwal Pakan",
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
                        Button(
                            modifier = Modifier,
                            shape = ShapeDefaults.Large,
                            colors = ButtonDefaults.buttonColors(Color.White),
                            onClick = {
                                // TODO: Fungsi buka pakan
                            }
                        ) {
                            Text(text = "Buka", color = Color.Black)
                        }

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

            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        context.startActivity(Intent(context, FoodMonitoringActivity::class.java))
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(2f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "80%",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Stok Makanan",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            modifier = Modifier,
                            colors = IconButtonDefaults.iconButtonColors(Color.White),
                            onClick = {
                                // TODO: tambah jadwal pakan
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_refresh_24),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clickable {
                    context.startActivity(Intent(context, EggMonitoringActivity::class.java))
                },
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            shape = ShapeDefaults.ExtraLarge
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 28.dp).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Suhu & Kelembapan Telur",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )


                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "28° C",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "70%",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        modifier = Modifier,
                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
                        onClick = {
                            // TODO: tambah jadwal pakan
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_refresh_24),
                            contentDescription = null,
                            tint = Color.White,

                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar Card
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        context.startActivity(Intent(context, SecurityAlarmActivity::class.java))
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Analisis Data",
                    )
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable {
                        // Analisis data activity here
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                shape = ShapeDefaults.ExtraLarge
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), // Mengisi seluruh area Card
                    contentAlignment = Alignment.Center // Memusatkan konten
                ) {
                    Text(
                        text = "Analisis Data",
                    )
                }
            }
        }
    }
}