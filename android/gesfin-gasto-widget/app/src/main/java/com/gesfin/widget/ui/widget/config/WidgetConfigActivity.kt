package com.gesfin.widget.ui.widget.config

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gesfin.widget.R
import com.gesfin.widget.core.datastore.SessionDataStore
import kotlinx.coroutines.launch

class WidgetConfigActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        
        setContent {
            WidgetConfigScreen(
                appWidgetId = appWidgetId,
                onConfigComplete = {
                    val resultIntent = Intent()
                    resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun WidgetConfigScreen(
    appWidgetId: Int,
    onConfigComplete: () -> Unit
) {
    val context = LocalContext.current
    val sessionDataStore = remember { SessionDataStore(context) }
    val sessionData by sessionDataStore.sessionData.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    
    var authToken by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var familyGroupId by remember { mutableStateOf("") }
    
    LaunchedEffect(sessionData) {
        sessionData?.let { data ->
            if (data.isLoggedIn) {
                authToken = data.authToken
                userId = data.userId.toString()
                nombreUsuario = data.nombreUsuario
                familyGroupId = data.familyGroupId.toString()
            }
        }
    }
    
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = context.getString(R.string.registrar_gasto),
                style = MaterialTheme.typography.headlineSmall
            )
            
            if (sessionData?.isLoggedIn == true) {
                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
                
                OutlinedTextField(
                    value = userId,
                    onValueChange = { },
                    label = { Text("User ID") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = familyGroupId,
                    onValueChange = { },
                    label = { Text("Family Group ID") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        scope.launch {
                            sessionData?.let { data ->
                                if (!data.isLoggedIn) {
                                    sessionDataStore.saveSession(
                                        authToken = authToken.ifEmpty { data.authToken },
                                        userId = userId.toLongOrNull() ?: data.userId,
                                        nombreUsuario = nombreUsuario.ifEmpty { data.nombreUsuario },
                                        familyGroupId = familyGroupId.toLongOrNull() ?: data.familyGroupId
                                    )
                                }
                            }
                            onConfigComplete()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar configuración")
                }
            } else {
                Text("Debes iniciar sesión para configurar el widget")
                
                Button(
                    onClick = {
                        val intent = Intent(context, com.gesfin.widget.ui.login.LoginActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Login")
                }
            }
        }
    }
}
