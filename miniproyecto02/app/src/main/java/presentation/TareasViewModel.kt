
package com.example.miniproyecto02.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyecto02.data.Tarea
import com.example.miniproyecto02.domain.AppDatabase
import kotlinx.coroutines.launch

class TareasViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Conexion con la base de datos
    private val dao = AppDatabase
        .getDatabase(application)
        .tareaDao()

    // Lista reactiva de tareas
    val tareas = dao.obtenerTodas()

    // Crear nueva tarea
    fun agregar(titulo: String, descripcion: String) {

        if (titulo.isBlank()) return

        viewModelScope.launch {
            dao.insertar(
                Tarea(
                    titulo = titulo.trim(),
                    descripcion = descripcion.trim()
                )
            )
        }
    }

    // Editar tarea existente
    fun editar(
        tarea: Tarea,
        titulo: String,
        descripcion: String
    ) {

        if (titulo.isBlank()) return

        viewModelScope.launch {
            dao.actualizar(
                tarea.copy(
                    titulo = titulo.trim(),
                    descripcion = descripcion.trim()
                )
            )
        }
    }

    // Marcar tarea como completada o pendiente
    fun cambiarEstado(
        tarea: Tarea,
        completada: Boolean
    ) {

        viewModelScope.launch {
            dao.actualizar(
                tarea.copy(completada = completada)
            )
        }
    }

    // Eliminar una tarea
    fun eliminar(tarea: Tarea) {

        viewModelScope.launch {
            dao.eliminar(tarea)
        }
    }

    // Borrar todas las tareas completadas
    fun borrarCompletadas() {

        viewModelScope.launch {
            dao.borrarCompletadas()
        }
    }
}
