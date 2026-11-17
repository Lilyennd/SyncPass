package com.example.syncpass.ui.asistentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Beneficiario(val nombre: String, val actividad: String)


val listaBeneficiarios = listOf(
    Beneficiario("Camila Rojas", "Trekking"),
    Beneficiario("Matías Soto", "Trekking"),
    Beneficiario("Valentina Díaz", "Trekking"),
    Beneficiario("Joaquín Pérez", "Trekking"),
    Beneficiario("Fernanda Muñoz", "Trekking"),
    Beneficiario("Sebastián Torres", "Trekking")
)

@Composable
fun AsistentesPantalla(onRegistrarAsistente: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Beneficiarios", style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = onRegistrarAsistente,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(Icons.Default.PersonAdd, contentDescription = "agregar")
            Spacer(Modifier.width(8.dp))
            Text("Registrar beneficiario")
        }

        listaBeneficiarios.forEach { beneficiario ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "beneficiario",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(beneficiario.nombre, fontWeight = FontWeight.Bold)
                        Text(
                            "Asignado a: ${beneficiario.actividad}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}