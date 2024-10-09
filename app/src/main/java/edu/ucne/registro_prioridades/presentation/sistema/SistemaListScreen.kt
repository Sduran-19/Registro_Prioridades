@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.sistema

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistro_prioridades.data.remote.dto.SistemaDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SistemaListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SistemaViewModel = hiltViewModel(),
    onCreateSistema: () -> Unit,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onCreateSistema = onCreateSistema,
        onEditSistema = onEditSistema,
        onDeleteSistema = onDeleteSistema
    )
}

@Composable
fun SistemaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: SistemaUiState,
    onCreateSistema: () -> Unit,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Sistemas",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Ir al menú")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateSistema,
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Sistema")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.sistemas) { sistema ->
                    SistemaRow(sistema, onEditSistema = onEditSistema, onDeleteSistema = onDeleteSistema)
                }
            }
        }
    }
}

@Composable
fun SistemaRow(
    sistema: SistemaDto,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SistemaId: ${sistema.sistemaId}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Nombre: ${sistema.nombre}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEditSistema(sistema.sistemaId ?: 0) },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(
                    onClick = { onDeleteSistema(sistema.sistemaId ?: 0) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

