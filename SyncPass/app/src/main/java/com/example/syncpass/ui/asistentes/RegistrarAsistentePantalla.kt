package com.example.syncpass.ui.asistentes

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncpass.ui.home.listaEventos

@Composable
fun RegistrarAsistentePantalla(onVolver: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Vecino") }
    var actividad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar asistente", style = MaterialTheme.typography.headlineMedium)
        Text("Para quienes llegan sin inscripción previa")

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre ") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Tipo de participante", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Estudiante", "Vecino", "Docente").forEach { opcion ->
                FilterChip(
                    selected = tipo == opcion,
                    onClick = { tipo = opcion },
                    label = { Text(opcion) }
                )
            }
        }

        Text("Actividad", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listaEventos.forEach { evento ->
                FilterChip(
                    selected = actividad == evento.titulo,
                    onClick = { actividad = evento.titulo },
                    label = { Text(evento.titulo) }
                )
            }
        }

        Button(
            onClick = {
                mensaje = if (nombre.isBlank() || actividad.isBlank()) {
                    "Escribe un nombre y elige una actividad"
                } else {
                    "Para una futura actualización"
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Registrar")
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