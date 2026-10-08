package com.gesfin.widget.data.remote.api

import com.gesfin.widget.data.remote.dto.request.CreateTransactionRequest
import com.gesfin.widget.data.remote.dto.response.TransactionReadDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/transacciones")
    suspend fun registrarTransaccion(@Body request: CreateTransactionRequest): Response<TransactionReadDto>
}
