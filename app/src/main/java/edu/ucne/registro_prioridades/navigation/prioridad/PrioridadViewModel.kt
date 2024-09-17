package edu.ucne.registro_prioridades.presentation.navigation.prioridad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_prioridades.Data.repository.PrioridadRepository
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {

            if (_uiState.value.descripcion.isBlank() || _uiState.value.diasCompromiso == null || _uiState.value.diasCompromiso!! < 1) {
                _uiState.update {
                    it.copy(errorMessage = "Debe ingresar todos los campos")
                }
            }
            if (prioridadRepository.exist(uiState.value.descripcion) != null) {
                _uiState.update {
                    it.copy(errorMessage = "Esta Descripción ya existe")
                }
            }
            else {
                prioridadRepository.save(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(errorMessage = "Agregado correctamente")

                }
                nuevo()
            }
        }
    }


    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getPrioridades().collect { prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            }
        }
    }

    fun getAll(): Flow<List<PrioridadEntity>> {
        return prioridadRepository.getPrioridades() // Simplemente retorna el Flow proporcionado por el repositorio
    }

    fun delete(prioridad: PrioridadEntity) {
        viewModelScope.launch {
            prioridadRepository.delete(prioridad)
        }
    }

    fun selectedPrioridad(prioridadId: Int) {
        viewModelScope.launch {
            if (prioridadId > 0) {
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update {
                    it.copy(
                        prioridadId = prioridad?.prioridadId,
                        descripcion = prioridad?.descripcion ?: "",
                        diasCompromiso = prioridad?.diasCompromiso
                    )
                }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                prioridadId = null,
                descripcion = "",
                diasCompromiso = null,
                errorMessage = null
            )
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onDiasCompromisoChange(diasCompromiso: String) {
        val dias = diasCompromiso.toIntOrNull()
        _uiState.update {
            it.copy(diasCompromiso = dias)
        }
    }

    fun onPrioridadIdChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

}