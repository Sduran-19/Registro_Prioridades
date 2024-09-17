package edu.ucne.prioridades.presentation.navigation.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import edu.ucne.registro_prioridades.local.entities.TicketEntity
import edu.ucne.registro_prioridades.navigation.ticket.UiState
import edu.ucne.registro_prioridades.presentation.navigation.prioridad.PrioridadViewModel
import edu.ucne.registro_prioridades.presentation.navigation.prioridad.SwipeToDeleteContainer


@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    viewModelPrioridad: PrioridadViewModel = hiltViewModel(),
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val prioridades by viewModelPrioridad.getAll().collectAsStateWithLifecycle(emptyList())

    TicketBodyListScreen(
        uiState = uiState,
        prioridades = prioridades,
        goToTicketScreen = goToTicketScreen,
        createTicket = createTicket,
        onDelete = viewModel::delete
    )
}

@Composable
fun TicketBodyListScreen(
    uiState: UiState,
    prioridades: List<PrioridadEntity>,
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit,
    onDelete: (TicketEntity) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTicket,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Lista de Tickets",
                fontSize = 29.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp),
                fontFamily = FontFamily.SansSerif
            )
            Row(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    "Cliente",
                    modifier = Modifier
                        .weight(1.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Prioridad",
                    modifier = Modifier
                        .weight(3f)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )

                Text(
                    "Asunto",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Fecha",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                items(
                    uiState.tickets,
                    key = { it.ticketId!! }
                ) { ticket ->
                    val prioridadDescripcion = prioridades.find { it.prioridadId == ticket.prioridadId }?.descripcion ?: "Sin prioridad"
                    SwipeToDeleteContainer(
                        item = ticket,
                        onDelete = onDelete
                    ) {
                        TicketRow(ticket, prioridadDescripcion, goToTicketScreen)
                    }
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    ticket: TicketEntity,
    prioridadDescripcion: String,
    goToTicketScreen: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                goToTicketScreen(ticket.prioridadId ?: 0)
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 15.dp)
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.cliente?:"",
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(3f),
            text = prioridadDescripcion,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = ticket.asunto.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = ticket.date.toString(),
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}
