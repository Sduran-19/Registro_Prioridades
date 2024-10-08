package edu.ucne.registro_prioridades.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registro_prioridades.local.database.PrioridadDb
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert
    suspend fun save(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades WHERE prioridadId = :id LIMIT 1")
    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadEntity>>

    // Método para buscar una prioridad por su descripción
    @Query("SELECT * FROM Prioridades WHERE descripcion = :descripcion LIMIT 1")
    suspend fun findByDescripcion(descripcion: String): PrioridadEntity?
}



