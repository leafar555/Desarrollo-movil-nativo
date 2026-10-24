
package com.example.practica08.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica08.data.Usuario
import com.example.practica08.domain.AppDatabase
import kotlinx.coroutines.launch

class UsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Conexion con Room Database
    private val dao = AppDatabase
        .getDatabase(application)
        .usuarioDao()

    // Lista reactiva de usuarios
    val usuarios = dao.obtenerTodos()

    // Guardar un nuevo usuario
    fun guardar(nombre: String) {

        if (nombre.isBlank()) return

        viewModelScope.launch {

            dao.insertar(
                Usuario(nombre = nombre.trim())
            )
        }
    }

    // Actualizar el nombre de un usuario
    fun actualizar(usuario: Usuario, nombre: String) {

        if (nombre.isBlank()) return

        viewModelScope.launch {

            dao.actualizar(
                usuario.copy(nombre = nombre.trim())
            )
        }
    }

    // Eliminar un usuario
    fun eliminar(usuario: Usuario) {

        viewModelScope.launch {

            dao.eliminar(usuario)
        }
    }
}
