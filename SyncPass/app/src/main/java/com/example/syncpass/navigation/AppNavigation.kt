package com.example.syncpass.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.syncpass.data.Sesion
import com.example.syncpass.ui.asistentes.AsistentesPantalla
import com.example.syncpass.ui.asistentes.RegistrarAsistentePantalla
import com.example.syncpass.ui.auth.LoginPantalla
import com.example.syncpass.ui.auth.RegistroPantalla
import com.example.syncpass.ui.encuesta.EncuestaPantalla
import com.example.syncpass.ui.home.CrearEventoPantalla
import com.example.syncpass.ui.home.DetalleEventoPantalla
import com.example.syncpass.ui.home.InicioPantalla
import com.example.syncpass.ui.miqr.MiQrPantalla
import com.example.syncpass.ui.perfil.PerfilPantalla
import com.example.syncpass.ui.scanner.EscanerPantalla


@Composable
fun SinAcceso(onVolver: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sin acceso", style = MaterialTheme.typography.headlineMedium)
        Text("Tu rol no tiene permiso para esta pantalla")
        Button(onClick = onVolver, modifier = Modifier.padding(top = 16.dp)) {
            Text("Volver")
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    val mostrarBarra = rutaActual == "inicio" ||
            rutaActual == "escanear" ||
            rutaActual == "asistentes" ||
            rutaActual == "encuesta" ||
            rutaActual == "miqr" ||
            rutaActual == "perfil"

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                BottomBar(navController, rutaActual)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginPantalla(
                    onLoginExitoso = {
                        navController.navigate("inicio") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onIrARegistro = { navController.navigate("registro") }
                )
            }

            composable("registro") {
                RegistroPantalla(onVolver = { navController.popBackStack() })
            }

            composable("inicio") {
                InicioPantalla(
                    onAbrirEvento = { id -> navController.navigate("detalle/$id") },
                    onCrearEvento = { navController.navigate("crear_evento") }
                )
            }

            composable(
                route = "detalle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                val id = entrada.arguments?.getInt("id") ?: 0
                DetalleEventoPantalla(
                    eventoId = id,
                    onVolver = { navController.popBackStack() }
                )
            }

            composable("crear_evento") {
                if (Sesion.usuario?.rol == "Coordinador") {
                    CrearEventoPantalla(onVolver = { navController.popBackStack() })
                } else {
                    SinAcceso(onVolver = { navController.popBackStack() })
                }
            }


            composable("escanear") {
                if (Sesion.usuario?.rol == "Beneficiario") {
                    SinAcceso(onVolver = { navController.popBackStack() })
                } else {
                    EscanerPantalla()
                }
            }

            composable("miqr") { MiQrPantalla() }


            composable("asistentes") {
                if (Sesion.usuario?.rol == "Beneficiario") {
                    SinAcceso(onVolver = { navController.popBackStack() })
                } else {
                    AsistentesPantalla(
                        onRegistrarAsistente = { navController.navigate("registrar_asistente") }
                    )
                }
            }

            composable("registrar_asistente") {
                if (Sesion.usuario?.rol == "Beneficiario") {
                    SinAcceso(onVolver = { navController.popBackStack() })
                } else {
                    RegistrarAsistentePantalla(onVolver = { navController.popBackStack() })
                }
            }

            composable("encuesta") { EncuestaPantalla() }

            composable("perfil") {
                PerfilPantalla(
                    onCerrarSesion = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}