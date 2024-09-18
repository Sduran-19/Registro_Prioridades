@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registro_prioridades.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TicketScreen(viewModel: TicketViewModel = hiltViewModel(), goBack: () -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        uiState = uiState.value,
        onFechaChange = viewModel::onFechaChange,
        onPrioridadChange = viewModel::onPrioridadIdChange,
        onClienteChange = viewModel::onClienteChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        save = viewModel::saveTicket,
        nuevo = viewModel::nuevoTicket,
        goBack = goBack
    )
}

@Composable
fun TicketBodyScreen(
    uiState: TicketViewModel.UiState,
    onFechaChange: (String) -> Unit,
    onPrioridadChange: (Int?) -> Unit,
    onClienteChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    save: () -> Unit,
    nuevo: () -> Unit,
    goBack: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPrioridadText by remember { mutableStateOf("Seleccionar Prioridad") }
    val prioridades = uiState.prioridades

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Registro de Tickets",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = selectedPrioridadText,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Prioridad") },
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown,
                            contentDescription = "Menú desplegable",
                            modifier = Modifier.clickable { expanded = !expanded })
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    prioridades.forEach { prioridad ->
                        DropdownMenuItem(
                            text = { Text(prioridad.descripcion) },
                            onClick = {
                                onPrioridadChange(prioridad.prioridadId)
                                selectedPrioridadText = prioridad.descripcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Fecha") },
                value = uiState.fecha,
                onValueChange = onFechaChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Cliente") },
                value = uiState.cliente,
                onValueChange = onClienteChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Asunto") },
                value = uiState.asunto,
                onValueChange = onAsuntoChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = uiState.descripcion,
                onValueChange = onDescripcionChange
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() }
                ) {
                    Text(text = "Guardar")
                    Icon(Icons.Filled.Add, contentDescription = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { nuevo() }
                ) {
                    Text(text = "Nuevo")
                    Icon(Icons.Filled.Refresh, contentDescription = "Nuevo")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
