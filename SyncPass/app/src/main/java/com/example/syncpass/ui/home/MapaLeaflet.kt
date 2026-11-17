package com.example.syncpass.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")


@Composable
fun MapaLeaflet(
    latitud: Double,
    longitud: Double,
    radioMetros: Int,
    modifier: Modifier = Modifier
) {
    // Página web con el mapa. Los $ meten las variables de Kotlin.
    val html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.css"/>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.js"></script>
            <style>
                html, body { height: 100%; margin: 0; padding: 0; background: #e0e0e0; }
                #map { height: 100%; width: 100%; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                if (typeof L === 'undefined') {
                    document.body.innerHTML = '<p style="padding:16px">No se pudo cargar el mapa. Revisa tu conexion a internet.</p>';
                } else {
                    var mapa = L.map('map').setView([$latitud, $longitud], 16);
                    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        maxZoom: 19,
                        attribution: '© OpenStreetMap'
                    }).addTo(mapa);
                    L.marker([$latitud, $longitud]).addTo(mapa);
                    L.circle([$latitud, $longitud], { radius: $radioMetros }).addTo(mapa);
                }
            </script>
        </body>
        </html>
    """.trimIndent()

    // AndroidView permite usar un WebView (vista clásica de Android) dentro de Compose
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                // El WebView ocupa todo el espacio que le da el modifier
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true    // Leaflet necesita JavaScript
                settings.domStorageEnabled = true

                // Escribe en Logcat los errores de carga (filtra por "Leaflet")
                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Log.e("Leaflet", "Error cargando ${request?.url}: ${error?.description}")
                    }
                }

                // Escribe en Logcat los errores de JavaScript
                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(msg: ConsoleMessage?): Boolean {
                        Log.e("Leaflet", "JS: ${msg?.message()}")
                        return true
                    }
                }

                loadDataWithBaseURL(
                    "https://syncpass.example",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}