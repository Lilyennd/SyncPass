package com.example.syncpass.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncpass.data.AppDatabase
import com.example.syncpass.data.Sesion
import kotlinx.coroutines.launch

@Composable
fun LoginPantalla(onLoginExitoso: () -> Unit, onIrARegistro: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Inicio sesión", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Usuario/Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = "sin conexión",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Estado: sin conexión", fontSize = 12.sp)
            }
        }

        Button(
            onClick = {
                if (correo.isBlank() || password.isBlank()) {
                    mensaje = "Completa todos los campos"
                } else {
                    scope.launch {
                        val dao = AppDatabase.obtener(context).usuarioDao()
                        val usuario = dao.login(correo.trim(), password)
                        if (usuario != null) {
                            Sesion.usuario = usuario
                            onLoginExitoso()
                        } else {
                            mensaje = "Correo o contraseña incorrectos"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        if (mensaje.isNotBlank()) {
            Text(mensaje, color = MaterialTheme.colorScheme.error)
        }

        TextButton(onClick = onIrARegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}