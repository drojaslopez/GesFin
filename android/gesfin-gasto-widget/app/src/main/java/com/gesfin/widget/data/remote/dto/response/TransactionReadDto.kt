package com.gesfin.widget.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionReadDto(
    val id: Long? = null,
    val familyGroupId: Long? = null,
    val userId: Long? = null,
    val monto: String? = null,
    val tipo: String? = null,
    val categoria: String? = null,
    val fecha: String? = null
)
