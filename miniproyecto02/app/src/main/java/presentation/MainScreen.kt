
package com.example.miniproyecto02.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.miniproyecto02.data.Tarea
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    tareasViewModel: TareasViewModel = viewModel()
) {

    // Obtener tareas desde Room Database
    val tareas by tareasViewModel.tareas.collectAsState(
        initial = emptyList()
    )

    // Estados de la interfaz
    var menuAbierto by remember {
        mutableStateOf(false)
    }

    var filtro by rememberSaveable {
        mutableStateOf("Todas")
    }

    var orden by rememberSaveable {
        mutableStateOf("Recientes")
    }

    var mostrarDialogo by rememberSaveable {
        mutableStateOf(false)
    }

    var confirmarBorrado by rememberSaveable {
        mutableStateOf(false)
    }

    var tituloTexto by rememberSaveable {
        mutableStateOf("")
    }

    var descripcionTexto by rememberSaveable {
        mutableStateOf("")
    }

    var idEditando by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    // Contar tareas segun su estado
    val pendientes = tareas.count { !it.completada }
    val completadas = tareas.count { it.completada }

    // Filtrar tareas
    val tareasFiltradas = when (filtro) {

        "Pendientes" -> tareas.filter {
            !it.completada
        }

        "Completadas" -> tareas.filter {
            it.completada
        }

        else -> tareas
    }

    // Ordenar tareas
    val tareasVisibles = when (orden) {

        "Antiguas" -> tareasFiltradas.sortedWith(
            compareBy<Tarea> { it.fechaCreacion }
                .thenBy { it.id }
        )

        "Estado" -> tareasFiltradas.sortedWith(
            compareBy<Tarea> { it.completada }
                .thenByDescending { it.fechaCreacion }
                .thenByDescending { it.id }
        )

        else -> tareasFiltradas.sortedWith(
            compareByDescending<Tarea> { it.fechaCreacion }
                .thenByDescending { it.id }
        )
    }

    Scaffold(

        // Barra superior de la aplicacion
        topBar = {

            TopAppBar(

                title = {
                    Text("Mis Tareas")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                ),

                actions = {

                    Box {

                        IconButton(
                            onClick = {
                                menuAbierto = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Opciones"
                            )
                        }

                        // Menu desplegable
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = {
                                menuAbierto = false
                            }
                        ) {

                            Text(
                                text = "Filtrar tareas",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.labelMedium
                            )

                            DropdownMenuItem(
                                text = { Text("Todas") },
                                onClick = {
                                    filtro = "Todas"
                                    menuAbierto = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Pendientes") },
                                onClick = {
                                    filtro = "Pendientes"
                                    menuAbierto = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Completadas") },
                                onClick = {
                                    filtro = "Completadas"
                                    menuAbierto = false
                                }
                            )

                            HorizontalDivider()

                            Text(
                                text = "Ordenar tareas",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.labelMedium
                            )

                            DropdownMenuItem(
                                text = { Text("Más recientes") },
                                onClick = {
                                    orden = "Recientes"
                                    menuAbierto = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Más antiguas") },
                                onClick = {
                                    orden = "Antiguas"
                                    menuAbierto = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Por estado") },
                                onClick = {
                                    orden = "Estado"
                                    menuAbierto = false
                                }
                            )

                            HorizontalDivider()

                            DropdownMenuItem(
                                text = { Text("Borrar completadas") },
                                enabled = completadas > 0,
                                onClick = {
                                    menuAbierto = false
                                    confirmarBorrado = true
                                }
                            )
                        }
                    }
                }
            )
        },

        // Boton flotante para crear tareas
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    idEditando = null
                    tituloTexto = ""
                    descripcionTexto = ""
                    mostrarDialogo = true
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva tarea"
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "Administrador de Tareas Locales",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Pendientes: $pendientes | Completadas: $completadas",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Filtro: $filtro | Orden: $orden",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (tareasVisibles.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay tareas para mostrar.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            } else {

                // Lista dinamica de tareas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(
                        items = tareasVisibles,
                        key = { it.id }
                    ) { tarea ->

                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Casilla para completar tarea
                                Checkbox(
                                    checked = tarea.completada,
                                    onCheckedChange = { marcado ->

                                        tareasViewModel.cambiarEstado(
                                            tarea,
                                            marcado
                                        )
                                    }
                                )

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = tarea.titulo,
                                        style = MaterialTheme.typography.titleMedium,
                                        textDecoration = if (tarea.completada) {
                                            TextDecoration.LineThrough
                                        } else {
                                            TextDecoration.None
                                        }
                                    )

                                    if (tarea.descripcion.isNotBlank()) {

                                        Text(
                                            text = tarea.descripcion,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    Text(
                                        text = if (tarea.completada) {
                                            "Completada"
                                        } else {
                                            "Pendiente"
                                        },
                                        style = MaterialTheme.typography.labelSmall
                                    )

                                    val fecha = SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm",
                                        Locale.getDefault()
                                    ).format(
                                        Date(tarea.fechaCreacion)
                                    )

                                    Text(
                                        text = fecha,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Boton para editar
                                IconButton(
                                    onClick = {
                                        idEditando = tarea.id
                                        tituloTexto = tarea.titulo
                                        descripcionTexto = tarea.descripcion
                                        mostrarDialogo = true
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${tarea.titulo}"
                                    )
                                }

                                // Boton para eliminar
                                IconButton(
                                    onClick = {
                                        tareasViewModel.eliminar(tarea)
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${tarea.titulo}"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Ventana para crear o editar tareas
    if (mostrarDialogo) {

        AlertDialog(

            onDismissRequest = {
                mostrarDialogo = false
            },

            title = {
                Text(
                    if (idEditando == null) {
                        "Nueva tarea"
                    } else {
                        "Editar tarea"
                    }
                )
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = tituloTexto,
                        onValueChange = {
                            tituloTexto = it
                        },
                        label = { Text("Título") },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = descripcionTexto,
                        onValueChange = {
                            descripcionTexto = it
                        },
                        label = { Text("Descripción") },
                        minLines = 2
                    )
                }
            },

            confirmButton = {

                TextButton(
                    enabled = tituloTexto.isNotBlank(),
                    onClick = {

                        if (idEditando == null) {

                            // Crear nueva tarea
                            tareasViewModel.agregar(
                                tituloTexto,
                                descripcionTexto
                            )

                        } else {

                            // Buscar tarea y actualizarla
                            val tarea = tareas.firstOrNull {
                                it.id == idEditando
                            }

                            if (tarea != null) {

                                tareasViewModel.editar(
                                    tarea,
                                    tituloTexto,
                                    descripcionTexto
                                )
                            }
                        }

                        mostrarDialogo = false
                        idEditando = null
                    }
                ) {
                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogo = false
                        idEditando = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Confirmar eliminacion de tareas completadas
    if (confirmarBorrado) {

        AlertDialog(

            onDismissRequest = {
                confirmarBorrado = false
            },

            title = {
                Text("Borrar tareas completadas")
            },

            text = {
                Text(
                    "¿Quieres eliminar todas las tareas completadas?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        tareasViewModel.borrarCompletadas()
                        confirmarBorrado = false
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        confirmarBorrado = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
