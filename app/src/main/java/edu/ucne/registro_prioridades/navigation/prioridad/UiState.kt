package edu.ucne.registro_prioridades.presentation.navigation.prioridad

import edu.ucne.registro_prioridades.local.entities.PrioridadEntity

data class UiState(
    val prioridadId: Int? = null,
    val descripcion: String = "",
    val diasCompromiso: Int? = 0,
    val errorMessage: String? = null,
    val prioridades: List<PrioridadEntity> = emptyList()

)

fun UiState.toEntity() = PrioridadEntity(
    prioridadId = prioridadId,
    descripcion = descripcion,
    diasCompromiso = diasCompromiso ?: 0
)