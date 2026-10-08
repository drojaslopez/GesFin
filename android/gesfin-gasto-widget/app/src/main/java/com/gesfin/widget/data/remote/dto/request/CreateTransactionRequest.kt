package com.gesfin.widget.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionRequest(
    val familyGroupId: Long,
    val userId: Long,
    val monto: String,
    val tipo: String = "GASTO",
    val categoria: String,
    val fecha: String
)
