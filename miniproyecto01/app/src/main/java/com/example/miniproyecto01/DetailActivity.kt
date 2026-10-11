package com.example.miniproyecto01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class DetailActivity : ComponentActivity() {

    companion object {

        const val EXTRA_MATRICULA =
            "EXTRA_MATRICULA"

        const val EXTRA_NOMBRE =
            "EXTRA_NOMBRE"

        const val EXTRA_CARRERA =
            "EXTRA_CARRERA"

        const val EXTRA_TURNO =
            "EXTRA_TURNO"

        const val EXTRA_ACTIVO =
            "EXTRA_ACTIVO"
    }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        // Recibir los parámetros del Intent

        val matricula =
            intent.getStringExtra(
                EXTRA_MATRICULA
            ) ?: ""

        val nombre =
            intent.getStringExtra(
                EXTRA_NOMBRE
            ) ?: ""

        val carrera =
            intent.getStringExtra(
                EXTRA_CARRERA
            ) ?: ""

        val turno =
            intent.getStringExtra(
                EXTRA_TURNO
            ) ?: ""

        val activo =
            intent.getBooleanExtra(
                EXTRA_ACTIVO,
                false
            )


        setContent {

            DetailScreen(

                matricula = matricula,

                nombre = nombre,

                carrera = carrera,

                turno = turno,

                activo = activo,

                onBackClick = {

                    finish()
                }
            )
        }
    }
}