package com.example.pblmobile.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.R
import com.example.pblmobile.ui.theme.PblMobileTheme

data class ButtonDetail(
    @DrawableRes val icon: Int,
    val label: String,
    val actionButton: @Composable () -> Unit,
    val color: Color = Color.Black
)

@Composable
fun GroupButton(buttonDetails: List<ButtonDetail>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeDefaults.ExtraLarge,
        colors = CardDefaults.cardColors(Color(0xFFF3F3F3))
    ) {
        Column {
            buttonDetails.forEachIndexed { index, buttonDetail ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 4.dp)
                        .then(if (index != buttonDetails.lastIndex) Modifier.padding(bottom = 4.dp) else Modifier),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(buttonDetail.icon),
                        contentDescription = buttonDetail.label,
                        modifier = Modifier.size(28.dp),
                        tint = buttonDetail.color
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = buttonDetail.label,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        color = buttonDetail.color
                    )

                    buttonDetail.actionButton()
                }
                if (index != buttonDetails.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 20.dp).align(Alignment.CenterHorizontally),
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupButtonPreview() {
    val buttonDetails = listOf(
        ButtonDetail(
            icon = R.drawable.baseline_logout_24,
            label = "Suhu & Kelembapan",
            actionButton = {
                TextButton(onClick = { }) {
                    Text(text = "Rotasi", color = MaterialTheme.colorScheme.primary)
                }
            },
        ), ButtonDetail(
            icon = R.drawable.baseline_refresh_24,
            label = "Timer Rak Telur",
            actionButton = {
                TextButton(onClick = { }) {
                    Text(text = "Rotasi", color = MaterialTheme.colorScheme.primary)
                }
            },
        ), ButtonDetail(
            icon = R.drawable.baseline_add_24,
            label = "Ubah Posisi Telur",
            actionButton = {
                TextButton(onClick = { }) {
                    Text(text = "Rotasi", color = MaterialTheme.colorScheme.primary)
                }
            },
        )
    )

    PblMobileTheme {
        GroupButton(buttonDetails = buttonDetails)
    }
}
