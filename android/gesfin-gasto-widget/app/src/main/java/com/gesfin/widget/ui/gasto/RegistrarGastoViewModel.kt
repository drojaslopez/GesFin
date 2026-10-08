package com.gesfin.widget.ui.gasto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.data.remote.dto.request.CreateTransactionRequest
import com.gesfin.widget.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class RegistroGastoUiState {
    data object Idle : RegistroGastoUiState()
    data object Loading : RegistroGastoUiState()
    data object Success : RegistroGastoUiState()
    data class Error(val message: String) : RegistroGastoUiState()
}

class RegistrarGastoViewModel(
    private val transactionRepository: TransactionRepository,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistroGastoUiState>(RegistroGastoUiState.Idle)
    val uiState: StateFlow<RegistroGastoUiState> = _uiState.asStateFlow()
    
    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario: StateFlow<String> = _nombreUsuario.asStateFlow()
    
    private val _familyGroupId = MutableStateFlow(-1L)
    val familyGroupId: StateFlow<Long> = _familyGroupId.asStateFlow()
    
    private val _userId = MutableStateFlow(-1L)
    val userId: StateFlow<Long> = _userId.asStateFlow()

    init {
        viewModelScope.launch {
            val session = sessionDataStore.sessionData.first()
            if (session.isLoggedIn) {
                _nombreUsuario.value = session.nombreUsuario
                _familyGroupId.value = session.familyGroupId
                _userId.value = session.userId
            }
        }
    }

    fun registrarGasto(
        concepto: String,
        nota: String,
        monto: String,
        fechaLocalDate: LocalDate
    ) {
        viewModelScope.launch {
            _uiState.value = RegistroGastoUiState.Loading
            
            val session = sessionDataStore.sessionData.first()
            if (!session.isLoggedIn || session.userId == -1L || session.familyGroupId == -1L) {
                _uiState.value = RegistroGastoUiState.Error("No hay sesión activa")
                return@launch
            }
            
            val categoria = buildString {
                append(concepto)
                if (nota.isNotBlank()) {
                    append(" - Nota: $nota")
                }
            }
            
            val fechaStr = fechaLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val montoStr = monto.replace(",", ".")
            
            val request = CreateTransactionRequest(
                familyGroupId = session.familyGroupId,
                userId = session.userId,
                monto = montoStr,
                tipo = "GASTO",
                categoria = categoria,
                fecha = fechaStr
            )
            
            transactionRepository.registrarGasto(request)
                .onSuccess {
                    _uiState.value = RegistroGastoUiState.Success
                }
                .onFailure { exception ->
                    _uiState.value = RegistroGastoUiState.Error(
                        exception.message ?: "Error al registrar gasto"
                    )
                }
        }
    }
    
    fun resetState() {
        _uiState.value = RegistroGastoUiState.Idle
    }
}
