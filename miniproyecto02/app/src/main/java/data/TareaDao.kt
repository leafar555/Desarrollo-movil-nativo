
package com.example.miniproyecto02.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    // Obtener todas las tareas
    @Query("SELECT * FROM tareas ORDER BY fechaCreacion DESC, id DESC")
    fun obtenerTodas(): Flow<List<Tarea>>

    // Guardar una tarea nueva
    @Insert
    suspend fun insertar(tarea: Tarea)

    // Actualizar una tarea existente
    @Update
    suspend fun actualizar(tarea: Tarea)

    // Eliminar una tarea
    @Delete
    suspend fun eliminar(tarea: Tarea)

    // Eliminar todas las tareas completadas
    @Query("DELETE FROM tareas WHERE completada = 1")
    suspend fun borrarCompletadas()
}
