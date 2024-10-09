package edu.ucne.registro_prioridades.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ScreenPrioridades {
    @Serializable
    data object PrioridadesList : ScreenPrioridades()

    @Serializable
    data class Prioridades(val prioridadId: Int) : ScreenPrioridades()

    @Serializable
    data class EditPrioridad(val prioridadId: Int) : ScreenPrioridades()

    @Serializable
    data class DeletePrioridad(val prioridadId: Int) : ScreenPrioridades()

    @Serializable
    data object TicketsList : ScreenPrioridades()

    @Serializable
    data class Tickets(val ticketId: Int) : ScreenPrioridades()

    @Serializable
    data class EditTicket(val ticketId: Int) : ScreenPrioridades()

    @Serializable
    data class DeleteTicket(val ticketId: Int) : ScreenPrioridades()

    @Serializable
    data object SistemaList : ScreenPrioridades()

    @Serializable
    data class Sistemas(val sistemaId: Int) : ScreenPrioridades()

    @Serializable
    data class EditSistema(val sistemaId: Int) : ScreenPrioridades()

    @Serializable
    data class DeleteSistema(val sistemaId: Int) : ScreenPrioridades()

    @Serializable
    data object ClienteList : ScreenPrioridades()

    @Serializable
    data class Clientes(val clienteId: Int) : ScreenPrioridades()

    @Serializable
    data class EditCliente(val clienteId: Int) : ScreenPrioridades()

    @Serializable
    data class DeleteCliente(val clienteId: Int) : ScreenPrioridades()

    @Serializable
    data object HomeScreen : ScreenPrioridades()
}
