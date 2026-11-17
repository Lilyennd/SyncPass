package com.example.syncpass.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncpass.data.Sesion

@Composable
fun InicioPantalla(onAbrirEvento: (Int) -> Unit, onCrearEvento: () -> Unit) {
    var expandido by remember { mutableStateOf(false) }
    val rol = Sesion.usuario?.rol ?: "Coordinador"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "perfil",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Hola ${Sesion.usuario?.nombre ?: "usuario"}!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("($rol)", fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (rol != "Beneficiario") {
            Surface(
                shape = RoundedCornerShape(6.dp),
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandido = !expandido }
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Archivos por subir", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Icon(
                            imageVector = if (expandido) Icons.Default.KeyboardArrowUp
                            else Icons.Default.KeyboardArrowDown,
                            contentDescription = "expandir"
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("•  Asistencia Trekking", fontSize = 13.sp)
                    if (expandido) {
                        Text("•  Encuesta Mente activa", fontSize = 13.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (rol == "Coordinador") {
            Button(
                onClick = onCrearEvento,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "crear")
                Spacer(Modifier.width(8.dp))
                Text("Crear evento")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Catálogo eventos", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "favoritos",
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        listaEventos.forEach { evento ->
            TarjetaEvento(
                evento = evento,
                onClick = { onAbrirEvento(evento.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun TarjetaEvento(evento: Evento, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 90.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Terrain,
                contentDescription = evento.titulo,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(evento.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(evento.resumen, fontSize = 12.sp, lineHeight = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = "cupos", modifier = Modifier.size(16.dp))
                Text(evento.cupos, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.AddCircleOutline, contentDescription = "cuándo", modifier = Modifier.size(16.dp))
                Text(evento.cuando, fontSize = 12.sp)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.PlayArrow, contentDescription = "ver detalle", modifier = Modifier.size(18.dp))
            }
        }
    }
}