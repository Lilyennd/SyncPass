package com.example.syncpass.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.syncpass.data.Sesion

@Composable
fun BottomBar(navController: NavController, rutaActual: String?) {
    val rol = Sesion.usuario?.rol ?: "Coordinador"

    NavigationBar {
        NavigationBarItem(
            selected = rutaActual == "inicio",
            onClick = { irA(navController, "inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        if (rol == "Coordinador" || rol == "Voluntario") {
            NavigationBarItem(
                selected = rutaActual == "escanear",
                onClick = { irA(navController, "escanear") },
                icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear") },
                label = { Text("Escanear") }
            )
        }

        if (rol == "Beneficiario") {
            NavigationBarItem(
                selected = rutaActual == "miqr",
                onClick = { irA(navController, "miqr") },
                icon = { Icon(Icons.Default.QrCode, contentDescription = "Mi QR") },
                label = { Text("Mi QR") }
            )
        }

        if (rol != "Beneficiario") {
            NavigationBarItem(
                selected = rutaActual == "asistentes",
                onClick = { irA(navController, "asistentes") },
                icon = { Icon(Icons.Default.Groups, contentDescription = "Beneficiarios") },
                label = { Text("Asistencias") }
            )
        }

        if (rol != "Voluntario") {
            NavigationBarItem(
                selected = rutaActual == "encuesta",
                onClick = { irA(navController, "encuesta") },
                icon = { Icon(Icons.Default.Star, contentDescription = "Encuesta") },
                label = { Text("Encuesta") }
            )
        }

        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = { irA(navController, "perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}

private fun irA(navController: NavController, ruta: String) {
    navController.navigate(ruta) {
        popUpTo("inicio") { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}