package edu.ucne.composeregistroprioridadesap2.presentation.cliente

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistro_prioridades.data.remote.dto.ClienteDto
import edu.ucne.registro_prioridades.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {

        getClientes()
    }

    private fun getClientes() {
        viewModelScope.launch {
            try {
                val clientes = clienteRepository.getAll()
                _uiState.update { it.copy(clientes = clientes) }
            } catch (e: Exception) {
                Log.e("ClienteViewModel", "Error obteniendo clientes: ${e.message}", e)
            }
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(nombre = nombre, errorNombre = null)
        }
    }

    fun onRncChange(rnc: String) {
        _uiState.update {
            it.copy(rnc = rnc, errorRNC = null)
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email, errorEmail = null)
        }
    }

    fun onDireccionChange(direccion: String) {
        _uiState.update {
            it.copy(direccion = direccion, errorDireccion = null)
        }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.nombre.isBlank()) {
                _uiState.update { it.copy(errorNombre = "Este campo no puede estar vacío") }
                return@launch
            }
            if (state.rnc.isBlank() || state.rnc.length != 11) {
                _uiState.update { it.copy(errorRNC = "El RNC debe tener exactamente 11 caracteres.") }
                return@launch
            }
            if (state.email.isBlank()) {
                _uiState.update { it.copy(errorEmail = "Este campo no puede estar vacío") }
                return@launch
            }
            if (state.direccion.isBlank()) {
                _uiState.update { it.copy(errorDireccion = "Este campo no puede estar vacío") }
                return@launch
            }

            try {
                clienteRepository.save(state.toEntity())
                _uiState.update { it.copy(success = true) }
                nuevo()
            } catch (e: Exception) {
                Log.e("ClienteViewModel", "Error al guardar el cliente: ${e.message}", e)
                _uiState.update { it.copy(errorMessages = "Error al guardar el cliente") }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                nombre = "",
                rnc = "",
                email = "",
                direccion = "",
                errorNombre = null,
                errorRNC = null,
                errorEmail = null,
                errorDireccion = null,
                success = false
            )
        }
    }

    fun selectedCliente(clienteId: Int) {
        viewModelScope.launch {
            if (clienteId > 0) {
                val cliente = clienteRepository.getClienteById(clienteId)
                _uiState.update {
                    it.copy(
                        clienteId = cliente.clienteId,
                        nombre = cliente.nombre,
                        rnc = cliente.rnc,
                        email = cliente.email,
                        direccion = cliente.direccion,
                        errorNombre = null,
                        errorRNC = null,
                        errorEmail = null,
                        errorDireccion = null,
                        success = false
                    )
                }
            }
        }
    }

    data class UiState(
        val clienteId: Int? = null,
        val nombre: String = "",
        val rnc: String = "",
        val email: String = "",
        val direccion: String = "",
        val errorNombre: String? = null,
        val errorRNC: String? = null,
        val errorEmail: String? = null,
        val errorDireccion: String? = null,
        val success: Boolean = false,
        val errorMessages: String? = null,
        val clientes: List<ClienteDto> = emptyList()
    )

    fun UiState.toEntity() = ClienteDto(
        clienteId = clienteId ?: 0,
        nombre = nombre,
        rnc = rnc,
        email = email,
        direccion = direccion,
        clientesDetalleCelulares = TODO(),
        clientesDetalleTelefonos = TODO()
    )
}
