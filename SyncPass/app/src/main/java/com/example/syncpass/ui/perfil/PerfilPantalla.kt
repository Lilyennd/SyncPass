package com.example.syncpass.ui.perfil

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncpass.data.ConfigTema
import com.example.syncpass.data.Sesion

@Composable
fun PerfilPantalla(onCerrarSesion: () -> Unit) {
    val usuario = Sesion.usuario
    val oscuroDelCelular = isSystemInDarkTheme()
    val oscuro = ConfigTema.modoOscuro ?: oscuroDelCelular

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "perfil",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(96.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(usuario?.nombre ?: "Sin nombre", style = MaterialTheme.typography.headlineMedium)
        Text(usuario?.rol ?: "", color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Correo", style = MaterialTheme.typography.labelMedium)
                Text(usuario?.correo ?: "-", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(12.dp))
                Text("Rol", style = MaterialTheme.typography.labelMedium)
                Text(usuario?.rol ?: "-", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (oscuro) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = "tema"
            )
            Spacer(Modifier.width(8.dp))
            Text("Modo oscuro", modifier = Modifier.weight(1f))
            Switch(
                checked = oscuro,
                onCheckedChange = { ConfigTema.modoOscuro = it }
            )
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = {
            Sesion.usuario = null
            onCerrarSesion()
        }) {
            Text("Cerrar sesión")
        }
    }
}