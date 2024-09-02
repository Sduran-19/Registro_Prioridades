package edu.ucne.registro_prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.registro_prioridades.ui.theme.Registro_PrioridadesTheme
import edu.ucne.registro_prioridades.local.database.PrioridadDb
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import kotlinx.coroutines.launch
import  edu.ucne.registro_prioridades.local.dao.PrioridadDao

class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration().build()

        setContent {
            Registro_PrioridadesTheme {
                PrioridadScreen(prioridadDb)
            }
        }
    }

    @Composable
    fun PrioridadScreen(prioridadDb: PrioridadDb) {
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Registro de Prioridades",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Descripción") },
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                        errorMessage = ""  // Limpiar mensaje de error al cambiar texto
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Días compromiso") },
                    value = diasCompromiso,
                    onValueChange = {
                        diasCompromiso = it
                        errorMessage = ""  // Limpiar mensaje de error al cambiar texto
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            if (descripcion.isBlank() || diasCompromiso.isBlank()) {
                                errorMessage = "Todos los campos son obligatorios."
                                return@launch
                            }

                            val prioridadExistente = prioridadDb.prioridadDao().findByDescripcion(descripcion)
                            if (prioridadExistente != null) {
                                errorMessage = "Ya existe una prioridad con esta descripción."
                                return@launch
                            }

                            val nuevaPrioridad = PrioridadEntity(
                                descripcion = descripcion,
                                diasCompromiso = diasCompromiso.toIntOrNull() ?: 0
                            )
                            prioridadDb.prioridadDao().save(nuevaPrioridad)
                            descripcion = ""
                            diasCompromiso = ""
                        }
                    }
                ) {
                    Text(text = "Guardar")
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                }

                Spacer(modifier = Modifier.height(8.dp))

                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )

                PrioridadListScreen(prioridadList)
            }
        }
    }


    @Composable
    fun PrioridadListScreen(prioridadList: List<PrioridadEntity>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

                Text(
                    text = "Prioridades:",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        textAlign = TextAlign.Center
                    )
                )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "ID",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Descripción",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Días Compromiso",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(prioridadList) { prioridad ->
                    PrioridadRow(prioridad)
                }
            }
        }
    }

    @Composable
    fun PrioridadRow(prioridad: PrioridadEntity) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = prioridad.prioridadId.toString(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(3f),
                text = prioridad.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = prioridad.diasCompromiso.toString(),
                textAlign = TextAlign.Center
            )
        }
        HorizontalDivider()
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Registro_PrioridadesTheme {
            //PrioridadScreen()
        }
    }
}