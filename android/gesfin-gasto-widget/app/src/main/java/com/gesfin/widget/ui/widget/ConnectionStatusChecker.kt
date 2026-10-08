package com.gesfin.widget.ui.widget

import android.content.Context
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.data.remote.api.ApiService
import com.gesfin.widget.di.AppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

data class ConnectionCheckResult(
    val status: ConnectionStatus,
    val message: String
)

class ConnectionStatusChecker(
    private val context: Context,
    private val apiService: ApiService? = null
) {
    
    private val sessionDataStore = SessionDataStore(context)
    
    suspend fun checkConnection(): ConnectionCheckResult = withContext(Dispatchers.IO) {
        try {
            Timber.d("Verificando conexión con backend...")
            val api = apiService ?: run {
                val authInterceptor = AppModule.provideAuthInterceptor(sessionDataStore)
                val okHttpClient = AppModule.provideOkHttpClient(authInterceptor)
                val retrofit = AppModule.provideRetrofit(okHttpClient)
                AppModule.provideApiService(retrofit)
            }
            
            val session = sessionDataStore.sessionData.first()
            val testResponse = api.registrarTransaccion(
                com.gesfin.widget.data.remote.dto.request.CreateTransactionRequest(
                    familyGroupId = session.familyGroupId.takeIf { it > 0 } ?: 1L,
                    userId = session.userId.takeIf { it > 0 } ?: 1L,
                    monto = "0.01",
                    tipo = "GASTO",
                    categoria = "TEST_CONEXION_WIDGET",
                    fecha = java.time.LocalDate.now().toString()
                )
            )
            when {
                testResponse.isSuccessful -> {
                    Timber.i("Conexión exitosa - Verde")
                    ConnectionCheckResult(ConnectionStatus.CONNECTED, "Conexión estable")
                }
                testResponse.code() in 400..499 -> {
                    Timber.w("Respuesta ${testResponse.code()} - Posiblemente validación (advertencia)")
                    ConnectionCheckResult(ConnectionStatus.WARNING, "Conexión válida (advertencia ${testResponse.code()})")
                }
                else -> {
                    Timber.e("Error HTTP ${testResponse.code()}")
                    ConnectionCheckResult(ConnectionStatus.ERROR, "Error HTTP ${testResponse.code()}")
                }
            }
        } catch (e: UnknownHostException) {
            Timber.e(e, "Host desconocido - Sin conexión")
            ConnectionCheckResult(ConnectionStatus.ERROR, "Sin conexión: host no resuelto")
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "Timeout de conexión")
            ConnectionCheckResult(ConnectionStatus.WARNING, "Lento: timeout de conexión")
        } catch (e: TimeoutException) {
            Timber.e(e, "Timeout")
            ConnectionCheckResult(ConnectionStatus.WARNING, "Lento: tiempo de espera excedido")
        } catch (e: java.io.IOException) {
            Timber.e(e, "IOException - Problema de red")
            ConnectionCheckResult(ConnectionStatus.ERROR, "Error de red: ${e.message ?: "desconocido"}")
        } catch (e: Exception) {
            Timber.e(e, "Error general de conexión")
            ConnectionCheckResult(ConnectionStatus.ERROR, "Error: ${e.message ?: "desconocido"}")
        }
    }
}
