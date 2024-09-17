package edu.ucne.registro_prioridades.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets")
class TicketEntity(
    @PrimaryKey
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val date: String? = null,
    val cliente: String? = null,
    val asunto: String? = null,
    val descripcion: String? = null,
)