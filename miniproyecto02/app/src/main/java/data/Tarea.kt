
package com.example.miniproyecto02.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla donde se almacenaran las tareas
@Entity(tableName = "tareas")
data class Tarea(

    // Identificador unico de cada tarea
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Nombre de la tarea
    val titulo: String,

    // Descripcion de la tarea
    val descripcion: String = "",

    // Indica si la tarea esta completada
    val completada: Boolean = false,

    // Fecha y hora de creacion
    val fechaCreacion: Long = System.currentTimeMillis()
)
