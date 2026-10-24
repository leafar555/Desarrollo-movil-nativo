
package com.example.practica08.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(
    usuarioViewModel: UsuarioViewModel = viewModel()
) {

    // Obtenemos los usuarios desde Room
    val usuarios by usuarioViewModel.usuarios.collectAsState(
        initial = emptyList()
    )

    // Nombre que escribe el usuario
    var nuevoNombre by rememberSaveable {
        mutableStateOf("")
    }

    // Usuario seleccionado para editar
    var idEditando by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Titulo principal
            Text(
                text = "Registro de Usuarios",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Campo para escribir nombres
            OutlinedTextField(
                value = nuevoNombre,
                onValueChange = {
                    nuevoNombre = it
                },
                label = {
                    Text("Nombre del usuario")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Boton para guardar o actualizar
            Button(
                onClick = {

                    val nombre = nuevoNombre.trim()

                    if (nombre.isNotEmpty()) {

                        if (idEditando == null) {

                            // Registrar usuario nuevo
                            usuarioViewModel.guardar(nombre)

                        } else {

                            // Buscar usuario para actualizar
                            val usuario = usuarios.firstOrNull {
                                it.id == idEditando
                            }

                            if (usuario != null) {

                                usuarioViewModel.actualizar(
                                    usuario,
                                    nombre
                                )
                            }
                        }

                        // Limpiar formulario
                        nuevoNombre = ""
                        idEditando = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = if (idEditando == null) {
                        "Guardar Usuario"
                    } else {
                        "Actualizar Usuario"
                    }
                )
            }

            // Cancelar la edicion
            if (idEditando != null) {

                TextButton(
                    onClick = {

                        nuevoNombre = ""
                        idEditando = null
                    }
                ) {
                    Text("Cancelar edición")
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Titulo de la lista
            Text(
                text = "Lista de Usuarios Registrados:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Mensaje cuando no hay registros
            if (usuarios.isEmpty()) {

                Text("No hay usuarios registrados.")
            }

            // Lista dinámica conectada a Room
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = usuarios,
                    key = { it.id }
                ) { usuario ->

                    // Tarjeta individual del usuario
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            // Mostrar los datos
                            Text(
                                text = "ID: ${usuario.id} | Nombre: ${usuario.nombre}",
                                modifier = Modifier.weight(1f)
                            )

                            // Boton para editar
                            TextButton(
                                onClick = {

                                    nuevoNombre = usuario.nombre
                                    idEditando = usuario.id
                                }
                            ) {
                                Text("Editar")
                            }

                            // Reto: boton para eliminar
                            IconButton(
                                onClick = {

                                    // Limpiar si se edita este usuario
                                    if (idEditando == usuario.id) {
                                        nuevoNombre = ""
                                        idEditando = null
                                    }

                                    usuarioViewModel.eliminar(usuario)
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription =
                                        "Eliminar ${usuario.nombre}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
