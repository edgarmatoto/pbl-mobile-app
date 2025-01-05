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
import com.example.pblmobile.apiService.model.JadwalPakanRequest
import com.example.pblmobile.component.PrimaryButton
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

@Composable
fun AddJadwalPakanScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State untuk input field
    var jam by remember { mutableStateOf("") }
    var detik by remember { mutableStateOf("") }

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
                    text = "Tambah Jadwal Pakan",
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
                JamInputField(jam) { jam = it }
                Spacer(Modifier.size(5.dp))

                DetikInputField(detik) { detik = it }
                Spacer(Modifier.size(20.dp))

                PrimaryButton(text = "Simpan") {
                    coroutineScope.launch {
                        val request = JadwalPakanRequest(jam = jam, detik = detik)
                        try {
                            val response = RetrofitInstance.api.addJadwalPakan(request)
                            if (response.status) {
                                Toast.makeText(context, "Jadwal Pakan berhasil diperbarui.", Toast.LENGTH_LONG).show()
                                navController.navigate("foodMonitoring") {
                                    popUpTo("addJadwalPakan") { inclusive = true }
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

@Composable
fun JamInputField(jam: String, onValueChange: (String) -> Unit) {
    var inputJam by remember { mutableStateOf(jam) }

    OutlinedTextField(
        label = { Text(text = "Jam") },
        placeholder = { Text(text = "00:00") },
        modifier = Modifier.fillMaxWidth(),
        value = inputJam,
        onValueChange = {
            inputJam = it
            onValueChange(it) // Update the state in the parent composable
        }
    )
}

@Composable
fun DetikInputField(detik: String, onValueChange: (String) -> Unit) {
    var inputDetik by remember { mutableStateOf(detik) }

    OutlinedTextField(
        label = { Text(text = "Durasi buka (detik)") },
        placeholder = { Text(text = "12") },
        modifier = Modifier.fillMaxWidth(),
        value = inputDetik,
        onValueChange = {
            inputDetik = it
            onValueChange(it) // Update the state in the parent composable
        }
    )
}

@Preview
@Composable
fun AddJadwalPakanScreenPreview() {
    PblMobileTheme {
        AddJadwalPakanScreen(NavController(LocalContext.current))
    }
}
