package edu.ucne.registro_prioridades.Data.repository

import edu.ucne.registro_prioridades.local.dao.PrioridadDao
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadDao: PrioridadDao
) {
    suspend fun save(prioridad: PrioridadEntity) = prioridadDao.save(prioridad)

    suspend fun getPrioridad(id:Int) = prioridadDao.find(id)

    suspend fun delete(prioridad: PrioridadEntity) = prioridadDao.delete(prioridad)

    fun getPrioridades() = prioridadDao.getAll()

    suspend fun exist(descripcion: String) = prioridadDao.findByDescripcion(descripcion)
}
