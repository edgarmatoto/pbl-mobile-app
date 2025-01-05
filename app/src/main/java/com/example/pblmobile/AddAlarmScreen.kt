import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pblmobile.apiService.RetrofitInstance
import com.example.pblmobile.apiService.model.AlarmRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.component.StringInputField
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

@Composable
fun AddAlarmScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State untuk input field
    var jam_mulai by remember { mutableStateOf("") }
    var jam_selesai by remember { mutableStateOf("") }
    var buzzer by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tambah Alarm",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            ) {
                StringInputField(data = jam_mulai, label = "Jam Mulai", placeholder = "Gunakan format 00:00") { jam_mulai = it }
                StringInputField(data = jam_selesai, label = "Jam Selesai", placeholder = "Gunakan format 00:00") { jam_selesai = it }
                Spacer(Modifier.size(5.dp))

                StringInputField(data = buzzer, label = "Durasi Bunyi Buzzer (detik)", placeholder = "Durasi (detik)") { buzzer = it }
                Spacer(Modifier.size(20.dp))

                PrimaryButton(text = "Simpan") {
                    coroutineScope.launch {
                        val request = AlarmRequest(jam_mulai = jam_mulai, jam_selesai = jam_mulai, buzzer = buzzer)
                        try {
                            val response = RetrofitInstance.api.addAlarm(request)
                            if (response.status) {
                                Toast.makeText(context, "Alarm berhasil diperbarui.", Toast.LENGTH_LONG).show()
                                navController.navigate("alarm") {
                                    popUpTo("addAlarm") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Gagal terhubung ke server: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        },
        bottomBar = {}
    )
}

@Preview
@Composable
fun AddAlarmScreenPreview() {
    PblMobileTheme {
        AddAlarmScreen(NavController(LocalContext.current))
    }
}
