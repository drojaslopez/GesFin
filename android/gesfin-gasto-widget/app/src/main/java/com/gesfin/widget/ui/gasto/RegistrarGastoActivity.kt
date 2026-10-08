package com.gesfin.widget.ui.gasto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gesfin.widget.R
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.data.repository.TransactionRepository
import com.gesfin.widget.di.AppModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class RegistrarGastoActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sessionDataStore = AppModule.provideSessionDataStore(this)
        val authInterceptor = AppModule.provideAuthInterceptor(sessionDataStore)
        val okHttpClient = AppModule.provideOkHttpClient(authInterceptor)
        val retrofit = AppModule.provideRetrofit(okHttpClient)
        val apiService = AppModule.provideApiService(retrofit)
        val transactionRepository = AppModule.provideTransactionRepository(apiService)
        
        val viewModel = RegistrarGastoViewModel(transactionRepository, sessionDataStore)
        
        setContent {
            val context = LocalContext.current
            val uiState by viewModel.uiState.collectAsState()
            val nombreUsuario by viewModel.nombreUsuario.collectAsState()
            val familyGroupId by viewModel.familyGroupId.collectAsState()
            
            LaunchedEffect(uiState) {
                when (uiState) {
                    is RegistroGastoUiState.Success -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.gasto_registrado_correctamente),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetState()
                        finish()
                    }
                    is RegistroGastoUiState.Error -> {
                        Toast.makeText(context, (uiState as RegistroGastoUiState.Error).message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
            
            RegistrarGastoScreen(
                nombreUsuario = nombreUsuario,
                familyGroupId = familyGroupId,
                uiState = uiState,
                onGuardar = { concepto, nota, monto, fecha ->
                    viewModel.registrarGasto(concepto, nota, monto, fecha)
                },
                onCancelar = { finish() }
            )
        }
    }
}

@Composable
fun RegistrarGastoScreen(
    nombreUsuario: String,
    familyGroupId: Long,
    uiState: RegistroGastoUiState,
    onGuardar: (String, String, String, LocalDate) -> Unit,
    onCancelar: () -> Unit
) {
    val context = LocalContext.current
    var concepto by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var usarOtraFecha by remember { mutableStateOf(false) }
    var fechaSeleccionada by remember { mutableStateOf(LocalDate.now()) }
    
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = context.getString(R.string.registrar_gasto),
                style = MaterialTheme.typography.headlineSmall
            )
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = context.getString(R.string.realizado_por, nombreUsuario),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = context.getString(R.string.grupo_familiar, familyGroupId.toString()),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            OutlinedTextField(
                value = concepto,
                onValueChange = { concepto = it },
                label = { Text(context.getString(R.string.concepto) + "*") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegistroGastoUiState.Loading
            )
            
            OutlinedTextField(
                value = nota,
                onValueChange = { nota = it },
                label = { Text(context.getString(R.string.nota)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegistroGastoUiState.Loading
            )
            
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text(context.getString(R.string.monto) + "*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                enabled = uiState !is RegistroGastoUiState.Loading
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = usarOtraFecha,
                    onCheckedChange = { usarOtraFecha = it },
                    enabled = uiState !is RegistroGastoUiState.Loading
                )
                Text(text = context.getString(R.string.cambiar_fecha))
            }
            
            if (usarOtraFecha) {
                OutlinedTextField(
                    value = fechaSeleccionada.toString(),
                    onValueChange = { },
                    label = { Text(context.getString(R.string.fecha)) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    enabled = uiState !is RegistroGastoUiState.Loading
                )
            } else {
                Text(
                    text = "${context.getString(R.string.fecha)}: ${LocalDate.now()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f),
                    enabled = uiState !is RegistroGastoUiState.Loading
                ) {
                    Text(context.getString(R.string.cancelar))
                }
                Button(
                    onClick = {
                        if (concepto.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (monto.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val montoDouble = monto.replace(",", ".").toDoubleOrNull()
                        if (montoDouble == null || montoDouble <= 0) {
                            Toast.makeText(context, context.getString(R.string.error_monto_invalido), Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val fecha = if (usarOtraFecha) fechaSeleccionada else LocalDate.now()
                        onGuardar(concepto, nota, monto, fecha)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = uiState !is RegistroGastoUiState.Loading
                ) {
                    Text(
                        text = if (uiState is RegistroGastoUiState.Loading) 
                            "Guardando..." 
                        else 
                            context.getString(R.string.guardar_gasto)
                    )
                }
            }
        }
    }
}
