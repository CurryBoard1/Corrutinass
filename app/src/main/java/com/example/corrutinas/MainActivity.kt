package com.example.corrutinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.corrutinas.ui.theme.CorrutinasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CorrutinasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Prueba(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Prueba(modifier: Modifier = Modifier) {
    // 1. Variables de estado para el porcentaje, mensaje y estado del botón
    var progreso by remember { mutableFloatStateOf(0f) }
    var mensaje by remember { mutableStateOf("Listo para iniciar descarga") }
    var estaDescargando by remember { mutableStateOf(false) }

    // 2. Scope de la corrutina ligado a la pantalla
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = mensaje, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Indicador de progreso que reacciona en tiempo real
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "${(progreso * 100).toInt()}%")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // 4. Se lanza la corrutina
                coroutineScope.launch {

                    // Codigo asincrono
                    estaDescargando = true
                    progreso = 0f
                    mensaje = "Descargando paquete..."

                    // El ciclo for incrementa el progreso paso a paso
                    for (i in 1..100) {
                        delay(40) // Pausa la corrutina 40 ms sin congelar la interfaz
                        progreso = i / 100f
                    }

                    mensaje = "¡Descarga completada!"
                    estaDescargando = false
                }
            },
            enabled = !estaDescargando
        ) {
            Text(if (estaDescargando) "Procesando..." else "Iniciar Descarga")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PruebaPreview() {
    CorrutinasTheme {
        Prueba()
    }
}
