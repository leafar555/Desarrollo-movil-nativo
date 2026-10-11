package com.example.miniproyecto01

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    activo: Boolean,
    onBackClick: () -> Unit
) {

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = "Registro del Estudiante",
                    fontSize = 26.sp,
                    style =
                        MaterialTheme.typography.headlineMedium
                )


                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )


                Card(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier =
                            Modifier.padding(20.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(14.dp)
                    ) {


                        Text(
                            text = "Matrícula:",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = matricula,
                            fontSize = 18.sp
                        )


                        Text(
                            text = "Nombre:",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = nombre,
                            fontSize = 18.sp
                        )


                        Text(
                            text = "Carrera:",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = carrera,
                            fontSize = 18.sp
                        )


                        Text(
                            text = "Turno:",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = turno,
                            fontSize = 18.sp
                        )


                        Text(
                            text = "Estatus:",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text =
                                if (activo) {
                                    "Activo"
                                } else {
                                    "Inactivo"
                                },

                            fontSize = 18.sp
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(28.dp)
                )


                Button(
                    onClick = onBackClick,
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        "Volver"
                    )
                }
            }
        }
    }
}