package edu.ucne.registro_prioridades.data.repository

import edu.ucne.composeregistro_prioridades.data.remote.api.ClientesApi
import edu.ucne.composeregistro_prioridades.data.remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: ClientesApi
){
    suspend fun getClienteById(clienteId: Int) = clienteApi.getCliente(clienteId)

    suspend fun save(clienteDto: ClienteDto) = clienteApi.save(clienteDto)

    suspend fun update(clienteDto: ClienteDto) = clienteApi.update(clienteDto)

    suspend fun delete(clienteId: Int) = clienteApi.delete(clienteId)

    suspend fun getAll() = clienteApi.getAll()
}