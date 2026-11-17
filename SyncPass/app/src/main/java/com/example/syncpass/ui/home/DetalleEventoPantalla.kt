package com.example.syncpass.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetalleEventoPantalla(eventoId: Int, onVolver: () -> Unit) {
    val evento = listaEventos.first { it.id == eventoId }
    var mostrarMapa by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(evento.titulo, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Detalles del evento", fontSize = 20.sp)

        Spacer(Modifier.height(12.dp))

        Text(evento.descripcion, fontSize = 14.sp, lineHeight = 20.sp)

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = "participantes", modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(evento.participantes, fontSize = 14.sp)
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Schedule, contentDescription = "horario", modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(evento.horario, fontSize = 14.sp)
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CalendarToday, contentDescription = "fecha", modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(evento.fecha, fontSize = 14.sp)
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "ubicación",
                modifier = Modifier.size(36.dp)
            )
            Spacer(Modifier.width(16.dp))
            OutlinedButton(
                onClick = { mostrarMapa = !mostrarMapa },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.width(150.dp).height(48.dp)
            ) {
                Text(if (mostrarMapa) "Ocultar mapa" else "Ubicación")
            }
        }

        if (mostrarMapa) {
            Spacer(Modifier.height(12.dp))
            Text("El círculo marca la zona válida para registrar asistencia (200 m).")
            Spacer(Modifier.height(8.dp))
            MapaLeaflet(
                latitud = evento.latitud,
                longitud = evento.longitud,
                radioMetros = 200,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(onClick = onVolver) {
            Text("Volver")
        }
    }
}