package com.example.pblmobile.onboarding

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.R
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    TextButton(
        modifier = modifier, onClick = onClick, colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Gray
        )
    ) {
        Text("Kembali")
    }
}

@Composable
fun NextButton(modifier: Modifier, onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick, modifier, colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {

        Icon(
            painter = painterResource(R.drawable.chevron_right), contentDescription = "Selanjutnya"
        )
    }
}

@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color.Gray
) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) {
            Spacer(modifier = Modifier.size(12.dp))

            Box(
                modifier = Modifier.height(9.dp).width(if (it == currentPage) 17.dp else 9.dp).clip(CircleShape)
                    .background(if (it == currentPage) selectedColor else unselectedColor)
            )

            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@Preview()
@Composable
fun IndicatorPreview() {
    PblMobileTheme {
        Indicator(pageSize = 3, currentPage = 0)

    }
}

@Preview
@Composable
fun BackButtonPreview() {
    BackButton(Modifier) {

    }
}

@Preview
@Composable
fun NextButtonPreview() {
    PblMobileTheme(dynamicColor = false) {
        NextButton(modifier = Modifier) {

        }
    }
}