package edu.ucne.composeregistro_prioridades.data.remote.api

import edu.ucne.composeregistro_prioridades.data.remote.dto.ClienteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientesApi {
    @GET("api/Clientes/{clienteId}")
    suspend fun getCliente(@Path("clienteId") clienteId: Int): ClienteDto

    @GET("api/Clientes")
    suspend fun getAll(): List<ClienteDto>

    @PUT("api/Clientes")
    suspend fun update(@Body clienteDto: ClienteDto?): ClienteDto

    @DELETE("api/Clientes/{clienteId}")
    suspend fun delete(@Path("clienteId") clienteId: Int)

    @POST("api/Clientes")
    suspend fun save(@Body clienteDto: ClienteDto?): ClienteDto
}