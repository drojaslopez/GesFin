package com.gesfin.widget.data.repository

import com.gesfin.widget.data.remote.api.ApiService
import com.gesfin.widget.data.remote.dto.request.CreateTransactionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepository(private val apiService: ApiService) {

    suspend fun registrarGasto(request: CreateTransactionRequest): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registrarTransaccion(request)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    Result.failure(Exception(errorBody.ifEmpty { "Error ${response.code()}" }))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
