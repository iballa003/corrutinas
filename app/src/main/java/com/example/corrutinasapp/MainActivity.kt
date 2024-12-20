package com.example.corrutinasapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corrutinasapp.ui.theme.CorrutinasAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CorrutinasAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
// Simula una conexión a una API
suspend fun fetchUserData(): Map<String, String> {
    delay(3000) // Simula un demora en red
    var result = mapOf<String,String>("username" to "Iballa", "Password" to "1234")
    return result
    //return "User data"
}

@Composable
fun Greeting(modifier: Modifier) {
    var results by remember { mutableStateOf("") }
    LaunchedEffect(key1 = "user") {//Para que se ejecute una vez
        CoroutineScope(Main).launch {//Crea y administra la corruptina
            try {
                val result = withContext(IO) { fetchUserData() }//
                results = result.toString()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
    Column(verticalArrangement = Arrangement.Center) {//Para que los datos se muestren en el centro de pantalla.
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Text(text = "Usuario y contraseña", fontWeight = FontWeight.Bold)
                Text(text = results)
            }

        }
    }

}
