@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.sistema

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_prioridades.Registro_PrioridadesTheme

@Composable
fun SistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    sistemaId: Int,
    goSistemas: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyScreen(
        sistemaId = sistemaId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goSistemas = goSistemas
    )
}

@Composable
fun SistemaBodyScreen(
    sistemaId: Int,
    uiState: SistemaUiState,
    onEvent: (SistemaUiEvent) -> Unit,
    goSistemas: () -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(SistemaUiEvent.SelectedSistema(sistemaId))

        if(uiState.success)
            goSistemas()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(sistemaId == 0) "Registro de Sistema" else "Modificar Sistema",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goSistemas) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Ir a la Lista de Sistemas")
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
                label = { Text("Nombre") },
                value = uiState.nombre ?: "",
                onValueChange = { onEvent(SistemaUiEvent.NombreChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .focusRequester(focusRequester)
                    .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onEvent(SistemaUiEvent.Save)
                    }
                )
            )
            uiState.errorNombre?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(SistemaUiEvent.Save) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Guardar Sistema")
                Text(text = if(sistemaId == 0) "Crear Sistema" else "Modificar Sistema")
            }
        }
    }
}
