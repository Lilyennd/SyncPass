package com.example.syncpass.ui.encuesta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EncuestaPantalla() {

    val actividad = "Trekking"


    val preguntas = listOf(
        "estrellas" to "¿Qué te pareció el instructor?",
        "caritas" to "¿Qué te parecieron las ayudas ofrecidas?",
        "estrellas" to "¿Cómo te trataron los voluntarios?"
    )


    val respuestas = remember { mutableStateMapOf<String, Int>() }
    var recomendaria by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val textoEstrellas = listOf("", "Muy malo", "Malo", "Regular", "Bueno", "Excelente")


    val caras = listOf(
        Icons.Default.SentimentVeryDissatisfied,
        Icons.Default.SentimentDissatisfied,
        Icons.Default.SentimentNeutral,
        Icons.Default.SentimentSatisfied,
        Icons.Default.SentimentVerySatisfied
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Encuesta de satisfacción", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Tienes una encuesta disponible por tu asistencia a la actividad de $actividad",
            style = MaterialTheme.typography.bodyMedium
        )


        preguntas.forEach { (tipo, pregunta) ->
            val valor = respuestas[pregunta] ?: 0

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(pregunta, style = MaterialTheme.typography.titleMedium)

                    if (tipo == "estrellas") {

                        Row {
                            for (numero in 1..5) {
                                IconButton(onClick = { respuestas[pregunta] = numero }) {
                                    Icon(
                                        imageVector = if (numero <= valor) Icons.Default.Star
                                        else Icons.Default.StarBorder,
                                        contentDescription = "$numero estrellas",
                                        tint = if (numero <= valor) Color(0xFFFFC107)
                                        else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }
                        if (valor > 0) {
                            Text(textoEstrellas[valor], color = MaterialTheme.colorScheme.primary)
                        }
                    } else {

                        Row {
                            for (numero in 1..5) {
                                IconButton(onClick = { respuestas[pregunta] = numero }) {
                                    Icon(
                                        imageVector = caras[numero - 1],
                                        contentDescription = "Opción $numero",
                                        tint = if (valor == numero) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Muy mal", style = MaterialTheme.typography.labelSmall)
                            Text("Muy bien", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }


        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("¿Lo recomendarías de nuevo?", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Sí", "No", "Tal vez").forEach { opcion ->
                        FilterChip(
                            selected = recomendaria == opcion,
                            onClick = { recomendaria = opcion },
                            label = { Text(opcion) }
                        )
                    }
                }
            }
        }


        Button(
            onClick = {
                val todasRespondidas = preguntas.all { (_, pregunta) ->
                    (respuestas[pregunta] ?: 0) > 0
                } && recomendaria.isNotBlank()

                mensaje = if (todasRespondidas) {
                    "Esta función aún se está implementando (para una futura actualización)"
                } else {
                    "Responde todas las preguntas antes de enviar"
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Icon(Icons.Default.Send, contentDescription = "enviar")
            Spacer(Modifier.width(8.dp))
            Text("Enviar encuesta")
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
    }
}