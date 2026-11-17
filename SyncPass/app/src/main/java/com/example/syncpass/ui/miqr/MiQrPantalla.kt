package com.example.syncpass.ui.miqr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.syncpass.data.Sesion

@Composable
fun MiQrPantalla() {
    val usuario = Sesion.usuario

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi código QR", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.QrCode,
            contentDescription = "código QR",
            modifier = Modifier.size(220.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = usuario?.nombre ?: "Beneficiario",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Muestra este código al voluntario para registrar tu asistencia",
            textAlign = TextAlign.Center
        )
    }
}