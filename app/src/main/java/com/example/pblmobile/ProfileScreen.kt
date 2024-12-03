package com.example.pblmobile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.navbar.Navigation
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 10.dp), contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Profil", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )
            }
        }
    },

        content = {
            Column(Modifier.padding(it)) {
                ProfileContent()
            }
        },

        bottomBar = {
            Navigation(navController)
        })
}

@Preview
@Composable
fun ProfileScreenPreview() {
    PblMobileTheme {
        ProfileScreen(NavController(LocalContext.current))
    }
}

@Composable
fun ProfileContent() {
    Column(Modifier.fillMaxSize().padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.prabowo),
            contentDescription = null,
            modifier = Modifier.size(120.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.size(18.dp))

        Text(
            text = "prabowo",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = "prabowosubianto@gmail.com",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(14.dp))

        PrimaryButton(modifier = Modifier.width(120.dp), text = "Edit profil", shape = ShapeDefaults.ExtraLarge) {
            /* Edit profile action */
        }
        Spacer(modifier = Modifier.size(18.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = "Keamanan",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA8A8A8)
                )
                Spacer(modifier = Modifier.size(12.dp))

                ProfileOption(icon = R.drawable.baseline_lock_outline_24, text = "Ganti password") {
                    Image(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = "Preferensi",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA8A8A8)
                )
                Spacer(modifier = Modifier.size(12.dp))

                ProfileOption(icon = R.drawable.baseline_notifications_none_24, text = "Nyalakan notifikasi") {
                    Image(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
                ProfileOption(icon = R.drawable.baseline_logout_24, text = "Logout", textColor = Color.Red) {
                    Image(imageVector = Icons.Default.ArrowForward, contentDescription = null, colorFilter = ColorFilter.tint(Color.Red))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    PblMobileTheme {
        ProfileContent()
    }
}

@Composable
fun ProfileOption(
    modifier: Modifier = Modifier, @DrawableRes icon: Int, text: String, textColor: Color = Color.Black, indicator: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().height(60.dp)
            .background(color = Color(0xFFF3F3F3), shape = ShapeDefaults.ExtraLarge)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(icon), contentDescription = null)
                Spacer(Modifier.size(12.dp))

                Text(text = text, style = MaterialTheme.typography.bodyMedium, color = textColor)
            }

            indicator()
        }
    }
}

@Preview
@Composable
fun ProfileOptionPreview() {
    ProfileOption(icon = R.drawable.baseline_lock_outline_24, text = "Ganti Password") {
        Image(imageVector = Icons.Default.ArrowForward, contentDescription = null)
    }
}