package com.example.syncpass.ui.home

data class Evento(
    val id: Int,
    val titulo: String,
    val resumen: String,
    val descripcion: String,
    val cupos: String,
    val cuando: String,
    val participantes: String,
    val horario: String,
    val fecha: String,
    val latitud: Double,
    val longitud: Double
)

val listaEventos = listOf(
    Evento(
        id = 1,
        titulo = "Trekking",
        resumen = "Ruta guiada por el Cerro Santa Lucía...",
        descripcion = "Ruta guiada por el Cerro Santa Lucía dirigida a vecinos y estudiantes " +
                "para fomentar el deporte al aire libre, identificando hitos históricos y " +
                "patrimoniales del trayecto. Incluye pausas de hidratación y taller breve " +
                "de senderismo urbano.",
        cupos = "18/30",
        cuando = "Hoy • 2 hr",
        participantes = "18 participantes",
        horario = "09:00 - 10:00 hrs.",
        fecha = "29/09/2026",
        latitud = -33.4410,
        longitud = -70.6440
    ),
    Evento(
        id = 2,
        titulo = "Mente activa",
        resumen = "Desafíos lúdicos, juegos de memoria y dinámicas grupales",
        descripcion = "Desafíos lúdicos, juegos de memoria y dinámicas grupales para estimular " +
                "la mente y fomentar la convivencia entre vecinos de distintas edades.",
        cupos = "15/30",
        cuando = "Martes • 1:20 hr",
        participantes = "15 participantes",
        horario = "10:00 - 11:20 hrs.",
        fecha = "30/09/2026",
        latitud = -33.4425,
        longitud = -70.6500
    ),
    Evento(
        id = 3,
        titulo = "Vida en marcha",
        resumen = "Sesiones de ejercicio adaptado, baile y yoga suave",
        descripcion = "Sesiones de ejercicio adaptado, baile y yoga suave pensadas para " +
                "mantener una vida activa y saludable en la comunidad.",
        cupos = "15/30",
        cuando = "Viernes • 1 hr",
        participantes = "15 participantes",
        horario = "11:00 - 12:00 hrs.",
        fecha = "02/10/2026",
        latitud = -33.4380,
        longitud = -70.6400
    )
)