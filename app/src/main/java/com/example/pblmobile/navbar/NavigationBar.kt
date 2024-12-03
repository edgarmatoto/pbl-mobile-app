package com.example.pblmobile.navbar

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.pblmobile.AboutActivity
import com.example.pblmobile.HomeActivity
import com.example.pblmobile.ProfileActivity
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var selectedItem by remember {
        mutableStateOf(sharedPreferences.getInt("selected_item", 0))
    }

    val items = listOf("Beranda", "Tentang Aplikasi", "Profil")
    val selectedIcons = listOf(Icons.Outlined.Home, Icons.Outlined.ThumbUp, Icons.Outlined.Person)
    val unselectedIcons = listOf(Icons.Outlined.Home, Icons.Outlined.ThumbUp, Icons.Outlined.Person)

    NavigationBar(
        modifier, containerColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = {
                Icon(
                    if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                    contentDescription = item,
                    tint = Color.White
                )
            }, label = { Text(item, color = Color.White) }, selected = selectedItem == index, onClick = {
                selectedItem = index

                // Simpan status ke SharedPreferences
                sharedPreferences.edit().putInt("selected_item", index).apply()

                when (index) {
                    0 -> context.startActivity(Intent(context, HomeActivity::class.java))


                    1 -> context.startActivity(Intent(context, AboutActivity::class.java))


                    2 -> context.startActivity(Intent(context, ProfileActivity::class.java))

                }
            })
        }
    }
}

@Preview
@Composable
fun NavigationPreview() {
    PblMobileTheme {
        Navigation()
    }
}