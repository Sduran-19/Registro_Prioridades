package edu.ucne.registro_prioridades.data.repository

import edu.ucne.composeregistro_prioridades.data.remote.api.SistemasApi
import edu.ucne.composeregistro_prioridades.data.remote.dto.SistemaDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemaApi: SistemasApi
){
    suspend fun getSistemaById(sistemaId: Int) = sistemaApi.getSistema(sistemaId)

    suspend fun save(sistemaDto: SistemaDto) = sistemaApi.save(sistemaDto)

    suspend fun update(sistemaDto: SistemaDto) = sistemaApi.update(sistemaDto)

    suspend fun delete(sistemaId: Int) = sistemaApi.delete(sistemaId)

    suspend fun getAll() = sistemaApi.getAll()
}