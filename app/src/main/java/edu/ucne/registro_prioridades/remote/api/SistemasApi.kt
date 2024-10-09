package edu.ucne.composeregistro_prioridades.data.remote.api

import edu.ucne.composeregistro_prioridades.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SistemasApi {
    @GET("api/Sistemas/{sistemaId}")
    suspend fun getSistema(@Path("sistemaId") sistemaId: Int): SistemaDto

    @GET("api/Sistemas")
    suspend fun getAll(): List<SistemaDto>

    @PUT("api/Sistemas")
    suspend fun update(@Body sistemaDto: SistemaDto?): SistemaDto

    @DELETE("api/Sistemas/{sistemaId}")
    suspend fun delete(@Path("sistemaId") sistemaId: Int)

    @DELETE("api/Sistemas/{sistemaId}")

    @POST("api/Sistemas")
    suspend fun save(@Body sistemaDto: SistemaDto?): SistemaDto
}