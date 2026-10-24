
package com.example.practica08.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    // Consultar todos los usuarios
    @Query("SELECT * FROM usuarios ORDER BY id ASC")
    fun obtenerTodos(): Flow<List<Usuario>>

    // Insertar un usuario
    @Insert
    suspend fun insertar(usuario: Usuario)

    // Actualizar un usuario
    @Update
    suspend fun actualizar(usuario: Usuario)

    // Eliminar un usuario
    @Delete
    suspend fun eliminar(usuario: Usuario)
}
