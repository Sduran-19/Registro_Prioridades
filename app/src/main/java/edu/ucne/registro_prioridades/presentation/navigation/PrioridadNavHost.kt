@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registro_prioridades.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composeregistro_prioridades.presentation.cliente.ClienteScreen
import edu.ucne.composeregistroprioridadesap2.presentation.cliente.ClienteListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.sistema.SistemaListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.sistema.SistemaScreen
import edu.ucne.registro_prioridades.NavigationItem
import edu.ucne.registro_prioridades.presentation.prioridad.*
import edu.ucne.registro_prioridades.presentation.ticket.*
import kotlinx.coroutines.launch

@Composable
fun registro_prioridadesNavHost(
    items: List<NavigationItem>,
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(Color(0xFFF5F5F5))
            ) {
                DrawerHeader(
                    modifier = Modifier.padding(16.dp),
                    text = "Registros"
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                items.forEachIndexed { index, navigationItem ->
                    val icon = when (navigationItem.title) {
                        "Prioridades" -> Icons.Filled.AddCircle
                        "Tickets" -> Icons.Filled.AddCircle
                        "Sistemas" -> Icons.Filled.List
                        "Clientes" -> Icons.Filled.AccountCircle
                        else -> Icons.Filled.AccountCircle
                    }

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = navigationItem.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (index == selectedItem) Color.Blue else Color.Black
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = navigationItem.title,
                                tint = if (index == selectedItem) Color.Blue else Color.Gray
                            )
                        },
                        selected = index == selectedItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem = index
                            navigateToScreen(navigationItem.title, navHostController)
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = Color(0xFFE0E0E0)
                        )
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navHostController,
            startDestination = ScreenPrioridades.PrioridadesList
        ) {
            composable<ScreenPrioridades.PrioridadesList> {
                PrioridadListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createPrioridad = {
                        navHostController.navigate(ScreenPrioridades.Prioridades(0))
                    },
                    onEditPrioridad = { prioridad ->
                        navHostController.navigate(ScreenPrioridades.EditPrioridad(prioridad))
                    },
                    onDeletePrioridad = { prioridad ->
                        navHostController.navigate(ScreenPrioridades.DeletePrioridad(prioridad))
                    }
                )
            }
            composable<ScreenPrioridades.TicketsList> {
                TicketListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createTicket = {
                        navHostController.navigate(ScreenPrioridades.Tickets(0))
                    },
                    onEditTicket = { ticket ->
                        navHostController.navigate(ScreenPrioridades.EditTicket(ticket))
                    },
                    onDeleteTicket = { ticket ->
                        navHostController.navigate(ScreenPrioridades.DeleteTicket(ticket))
                    }
                )
            }
            composable<ScreenPrioridades.ClienteList> {
                ClienteListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onCreateCliente = {
                        navHostController.navigate(ScreenPrioridades.Clientes(0))
                    },
                    onEditCliente = { clienteId ->
                        navHostController.navigate(ScreenPrioridades.EditCliente(clienteId))
                    },
                    onDeleteCliente = { clienteId ->
                        navHostController.navigate(ScreenPrioridades.DeleteCliente(clienteId))
                    }
                )
            }
            composable<ScreenPrioridades.SistemaList> {
                SistemaListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onCreateSistema = {
                        navHostController.navigate(ScreenPrioridades.Sistemas(0))
                    },
                    onEditSistema = { sistemaId ->
                        navHostController.navigate(ScreenPrioridades.EditSistema(sistemaId))
                    },
                    onDeleteSistema = { sistemaId ->
                        navHostController.navigate(ScreenPrioridades.DeleteSistema(sistemaId))
                    }
                )
            }

            composable<ScreenPrioridades.Clientes> { argumento ->
                val clienteId = argumento.toRoute<ScreenPrioridades.Clientes>().clienteId
                ClienteScreen(
                    goClientes = {
                        navHostController.navigate(ScreenPrioridades.ClienteList)
                    }
                )
            }

            composable<ScreenPrioridades.Sistemas> { argumento ->
                val sistemaId = argumento.toRoute<ScreenPrioridades.Sistemas>().sistemaId
                SistemaScreen(
                    sistemaId = sistemaId,
                    goSistemas = {
                        navHostController.navigate(ScreenPrioridades.SistemaList)
                    }
                )
            }

            composable<ScreenPrioridades.Prioridades> {
                val args = it.toRoute<ScreenPrioridades.Prioridades>()
                PrioridadScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<ScreenPrioridades.Tickets> {
                val args = it.toRoute<ScreenPrioridades.Tickets>()
                TicketScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable<ScreenPrioridades.EditPrioridad> {
                val args = it.toRoute<ScreenPrioridades.EditPrioridad>()
                EditPrioridadScreen(
                    prioridadId = args.prioridadId,
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<ScreenPrioridades.EditTicket> {
                val args = it.toRoute<ScreenPrioridades.EditTicket>()
                EditTicketScreen(
                    ticketId = args.ticketId,
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<ScreenPrioridades.DeletePrioridad> {
                val args = it.toRoute<ScreenPrioridades.DeletePrioridad>()
                DeletePrioridadScreen(
                    prioridadId = args.prioridadId,
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<ScreenPrioridades.DeleteTicket> {
                val args = it.toRoute<ScreenPrioridades.DeleteTicket>()
                DeleteTicketScreen(
                    ticketId = args.ticketId,
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}

// Función para navegar según el título
private fun navigateToScreen(title: String, navHostController: NavHostController) {
    when (title) {
        "Prioridades" -> navHostController.navigate(ScreenPrioridades.PrioridadesList)
        "Tickets" -> navHostController.navigate(ScreenPrioridades.TicketsList)
        "Sistemas" -> navHostController.navigate(ScreenPrioridades.SistemaList)
        "Clientes" -> navHostController.navigate(ScreenPrioridades.ClienteList)
    }
}

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

