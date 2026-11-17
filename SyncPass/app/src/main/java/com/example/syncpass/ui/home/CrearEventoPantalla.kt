package com.example.syncpass.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CrearEventoPantalla(onVolver: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var cupos by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Crear evento", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del evento") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (dd/mm/aaaa)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = horario,
            onValueChange = { horario = it },
            label = { Text("Horario (ej: 09:00 - 10:00)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cupos,
            onValueChange = { cupos = it },
            label = { Text("Cupos máximos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lugar,
            onValueChange = { lugar = it },
            label = { Text("Lugar / unidad vecinal") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                mensaje = if (nombre.isBlank() || fecha.isBlank() || cupos.isBlank()) {
                    "Completa al menos nombre, fecha y cupos"
                } else {
                    "Para una futura actualización"
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Crear evento")
        }

        if (mensaje.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "aviso",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(mensaje, color = MaterialTheme.colorScheme.primary)
            }
        }

        OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}