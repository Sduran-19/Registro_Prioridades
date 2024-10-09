@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistro_prioridades.presentation.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistroprioridadesap2.presentation.cliente.ClienteViewModel

@Composable
fun ClienteScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    goClientes: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ClienteBodyScreen(
        uiState = uiState.value,
        onNombreChange = viewModel::onNombreChange,
        onRncChange = viewModel::onRncChange,
        onEmailChange = viewModel::onEmailChange,
        onDireccionChange = viewModel::onDireccionChange,
        save = viewModel::save,
        goClientes = goClientes
    )
}

@Composable
fun ClienteBodyScreen(
    uiState: ClienteViewModel.UiState,
    onNombreChange: (String) -> Unit,
    onRncChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    save: () -> Unit,
    goClientes: () -> Unit
) {
    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success) goClientes()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Crear Cliente",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goClientes) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Ir hacia la lista de Clientes")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre") },
                value = uiState.nombre ?: "",
                onValueChange = onNombreChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("RNC") },
                value = uiState.rnc ?: "",
                onValueChange = onRncChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                value = uiState.email ?: "",
                onValueChange = onEmailChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Dirección") },
                value = uiState.direccion ?: "",
                onValueChange = onDireccionChange
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { save() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Guardar Cliente")
                Text(text = "Crear Cliente")
            }

            Spacer(modifier = Modifier.height(24.dp))

            uiState.errorNombre?.let {
                Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
            }

            uiState.errorRNC?.let {
                Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
            }
            uiState.errorEmail?.let {
                Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
            }
            uiState.errorDireccion?.let {
                Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}
