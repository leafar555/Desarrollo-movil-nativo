package com.example.miniproyecto01.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "StudentPreferences",
            Context.MODE_PRIVATE
        )

    companion object {

        const val KEY_MATRICULA = "key_matricula"
        const val KEY_NOMBRE = "key_nombre"
        const val KEY_CARRERA = "key_carrera"
        const val KEY_TURNO = "key_turno"
        const val KEY_ACTIVO = "key_activo"
    }


    // Guardar datos del estudiante
    fun saveStudent(
        matricula: String,
        nombre: String,
        carrera: String,
        turno: String,
        activo: Boolean
    ) {

        val editor =
            sharedPreferences.edit()

        editor.putString(
            KEY_MATRICULA,
            matricula
        )

        editor.putString(
            KEY_NOMBRE,
            nombre
        )

        editor.putString(
            KEY_CARRERA,
            carrera
        )

        editor.putString(
            KEY_TURNO,
            turno
        )

        editor.putBoolean(
            KEY_ACTIVO,
            activo
        )

        editor.apply()
    }


    // Recuperar matrícula
    fun getMatricula(): String {

        return sharedPreferences.getString(
            KEY_MATRICULA,
            ""
        ) ?: ""
    }


    // Recuperar nombre
    fun getNombre(): String {

        return sharedPreferences.getString(
            KEY_NOMBRE,
            ""
        ) ?: ""
    }


    // Recuperar carrera
    fun getCarrera(): String {

        return sharedPreferences.getString(
            KEY_CARRERA,
            "Selecciona una carrera"
        ) ?: "Selecciona una carrera"
    }


    // Recuperar turno
    fun getTurno(): String {

        return sharedPreferences.getString(
            KEY_TURNO,
            "Matutino"
        ) ?: "Matutino"
    }


    // Recuperar estatus
    fun getActivo(): Boolean {

        return sharedPreferences.getBoolean(
            KEY_ACTIVO,
            true
        )
    }


    // Borrar todos los datos guardados
    fun clearStudent() {

        sharedPreferences
            .edit()
            .clear()
            .apply()
    }
}