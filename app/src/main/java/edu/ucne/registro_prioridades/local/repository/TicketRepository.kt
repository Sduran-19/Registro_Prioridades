package edu.ucne.registro_prioridades.Data.repository

import edu.ucne.registro_prioridades.local.dao.TicketDao
import edu.ucne.registro_prioridades.local.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao

) {
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun getTicket(id:Int) = ticketDao.find(id)

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getTickets() = ticketDao.getAll()

}