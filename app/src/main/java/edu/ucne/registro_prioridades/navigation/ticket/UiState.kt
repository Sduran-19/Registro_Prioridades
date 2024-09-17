package edu.ucne.registro_prioridades.navigation.ticket

import edu.ucne.registro_prioridades.local.entities.TicketEntity

data class UiState(
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val date: String? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)

fun UiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    prioridadId = prioridadId,
    date = date,
    cliente = cliente,
    asunto = asunto,
    descripcion = descripcion
)