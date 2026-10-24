
package com.example.miniproyecto02.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miniproyecto02.data.Tarea
import com.example.miniproyecto02.data.TareaDao

@Database(
    entities = [Tarea::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Creamos una sola instancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tareas_locales.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
