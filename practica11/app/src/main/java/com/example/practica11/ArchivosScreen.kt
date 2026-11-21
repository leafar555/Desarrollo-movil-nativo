package com.example.practica11

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val NOMBRE_ARCHIVO = "mi_archivo.txt"

// Devuelve la carpeta según el destino elegido
fun obtenerCarpeta(context: Context, destino: String): File {
    return if (destino == "interno") {
        context.filesDir // memoria interna de la app
    } else {
        // getExternalFilesDirs: posición 0 = almacenamiento externo principal,
        // posición 1 = SDCard (si el dispositivo/emulador tiene una)
        val carpetas = context.getExternalFilesDirs(null)
        carpetas.getOrNull(1) ?: carpetas.getOrNull(0) ?: context.filesDir
    }
}

// Escribe en el archivo (agregar = true añade al final, false lo sobrescribe)
fun escribirArchivo(carpeta: File, texto: String, agregar: Boolean): String {
    val archivo = File(carpeta, NOMBRE_ARCHIVO)
    FileOutputStream(archivo, agregar).use { salida ->
        salida.write(texto.toByteArray())
    }
    return archivo.absolutePath
}

// Lee el archivo completo; devuelve null si no existe
fun leerArchivo(carpeta: File): String? {
    val archivo = File(carpeta, NOMBRE_ARCHIVO)
    if (!archivo.exists()) return null
    return FileInputStream(archivo).use { entrada ->
        entrada.readBytes().toString(Charsets.UTF_8)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivosScreen() {
    val context = LocalContext.current

    // Estados de la pantalla
    var texto by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("interno") } // "interno" o "externo"
    var contenidoLeido by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    // Verifica si ya se tienen los permisos de almacenamiento
    fun tienePermisos(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Lanzador para pedir los permisos en tiempo de ejecución
    val permisosLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        val todosConcedidos = resultado.values.all { it }
        mensaje = if (todosConcedidos) {
            "Permisos concedidos"
        } else {
            "Permisos denegados. Se usará la carpeta propia de la app."
        }
    }

    // Al abrir la app, pedimos los permisos si no los tiene
    LaunchedEffect(Unit) {
        if (!tienePermisos()) {
            permisosLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archivos y SDCard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo de texto
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Escribe tu texto") },
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de destino
            Text("Destino:", style = MaterialTheme.typography.titleMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { destino = "interno" }
            ) {
                RadioButton(selected = destino == "interno", onClick = { destino = "interno" })
                Text("Almacenamiento interno")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { destino = "externo" }
            ) {
                RadioButton(selected = destino == "externo", onClick = { destino = "externo" })
                Text("SDCard / Almacenamiento externo")
            }

            // Botón de permisos
            OutlinedButton(
                onClick = {
                    permisosLauncher.launch(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Solicitar permisos") }

            // Botón Guardar (sobrescribe)
            Button(
                onClick = {
                    try {
                        val ruta = escribirArchivo(obtenerCarpeta(context, destino), texto, false)
                        mensaje = "Guardado en:\n$ruta"
                    } catch (e: IOException) {
                        mensaje = "Error al guardar: ${e.message}"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar (sobrescribir)") }

            // Botón Agregar (modifica el archivo existente)
            Button(
                onClick = {
                    try {
                        val ruta = escribirArchivo(obtenerCarpeta(context, destino), "\n$texto", true)
                        mensaje = "Texto agregado en:\n$ruta"
                    } catch (e: IOException) {
                        mensaje = "Error al agregar: ${e.message}"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Agregar al final") }

            // Botón Leer
            Button(
                onClick = {
                    try {
                        val resultado = leerArchivo(obtenerCarpeta(context, destino))
                        if (resultado == null) {
                            contenidoLeido = ""
                            mensaje = "El archivo no existe en este destino"
                        } else {
                            contenidoLeido = resultado
                            mensaje = "Archivo leído correctamente"
                        }
                    } catch (e: IOException) {
                        mensaje = "Error al leer: ${e.message}"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Leer archivo") }

            // Mensajes de estado
            if (mensaje.isNotEmpty()) {
                Text(mensaje, style = MaterialTheme.typography.bodyMedium)
            }

            // Contenido leído
            if (contenidoLeido.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Contenido del archivo:", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(contenidoLeido)
                    }
                }
            }
        }
    }
}