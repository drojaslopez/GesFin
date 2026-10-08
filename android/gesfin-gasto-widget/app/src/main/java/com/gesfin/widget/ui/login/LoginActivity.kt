package com.gesfin.widget.ui.login

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gesfin.widget.R
import com.gesfin.widget.core.datastore.SessionDataStore
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen(
                onLoginSuccess = { finish() }
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val sessionDataStore = remember { SessionDataStore(context) }
    val scope = rememberCoroutineScope()
    
    var authToken by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var familyGroupId by remember { mutableStateOf("") }
    
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall
            )
            
            OutlinedTextField(
                value = authToken,
                onValueChange = { authToken = it },
                label = { Text("Token JWT (opcional para dev)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre Usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = familyGroupId,
                onValueChange = { familyGroupId = it },
                label = { Text("Family Group ID") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    scope.launch {
                        sessionDataStore.saveSession(
                            authToken = authToken,
                            userId = userId.toLongOrNull() ?: -1L,
                            nombreUsuario = nombreUsuario,
                            familyGroupId = familyGroupId.toLongOrNull() ?: -1L
                        )
                        onLoginSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
        }
    }
}
