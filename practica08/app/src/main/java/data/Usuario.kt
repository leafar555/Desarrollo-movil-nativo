
package com.example.practica08.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de usuarios de la base de datos
@Entity(tableName = "usuarios")
data class Usuario(

    // Identificador automatico
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Nombre del usuario
    val nombre: String
)