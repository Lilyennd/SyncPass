package com.example.syncpass.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.syncpass.data.AppDatabase
import com.example.syncpass.data.Usuario
import kotlinx.coroutines.launch

@Composable
fun RegistroPantalla(onVolver: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmacion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var rol by remember { mutableStateOf("Coordinador") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmacion,
            onValueChange = { confirmacion = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmacion.isNotEmpty() && password != confirmacion,
            supportingText = {
                if (confirmacion.isNotEmpty() && password != confirmacion) {
                    Text("Las contraseñas no coinciden")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Coordinador", "Voluntario", "Beneficiario").forEach { opcion ->
                FilterChip(
                    selected = rol == opcion,
                    onClick = { rol = opcion },
                    label = { Text(opcion) }
                )
            }
        }

        Button(
            onClick = {
                if (nombre.isBlank() || correo.isBlank() ||
                    password.isBlank() || confirmacion.isBlank()
                ) {
                    mensaje = "Completa todos los campos"
                } else if (!correo.contains("@")) {
                    mensaje = "El correo no es válido"
                } else if (password.length < 6) {
                    mensaje = "La contraseña debe tener al menos 6 caracteres"
                } else if (password != confirmacion) {
                    mensaje = "Las contraseñas no coinciden"
                } else {
                    scope.launch {
                        val dao = AppDatabase.obtener(context).usuarioDao()
                        val correoLimpio = correo.trim()
                        if (dao.buscarPorCorreo(correoLimpio) != null) {
                            mensaje = "Ese correo ya está registrado"
                        } else {
                            dao.insertar(
                                Usuario(
                                    nombre = nombre,
                                    correo = correoLimpio,
                                    password = password,
                                    rol = rol
                                )
                            )
                            Toast.makeText(context, "Usuario creado, inicia sesión", Toast.LENGTH_SHORT).show()
                            onVolver()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear usuario")
        }

        if (mensaje.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Aviso",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(mensaje, color = MaterialTheme.colorScheme.primary)
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}