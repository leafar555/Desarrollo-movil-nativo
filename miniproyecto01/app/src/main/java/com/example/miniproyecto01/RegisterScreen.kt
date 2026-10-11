package com.example.miniproyecto01

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniproyecto01.data.PreferencesManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {

    val context =
        LocalContext.current


    val preferencesManager =
        remember {

            PreferencesManager(context)
        }


    // Datos del formulario

    var matricula by remember {
        mutableStateOf("")
    }

    var nombre by remember {
        mutableStateOf("")
    }

    var carrera by remember {
        mutableStateOf(
            "Selecciona una carrera"
        )
    }

    var turno by remember {
        mutableStateOf("Matutino")
    }

    var activo by remember {
        mutableStateOf(true)
    }


    // Estado del menú desplegable

    var expanded by remember {
        mutableStateOf(false)
    }


    val carreras =
        listOf(
            "Ingeniería de Software",
            "Contaduría Pública",
            "Administración",
            "Informática"
        )


    // Recuperar datos guardados al abrir la aplicación

    LaunchedEffect(Unit) {

        matricula =
            preferencesManager
                .getMatricula()

        nombre =
            preferencesManager
                .getNombre()

        carrera =
            preferencesManager
                .getCarrera()

        turno =
            preferencesManager
                .getTurno()

        activo =
            preferencesManager
                .getActivo()
    }


    MaterialTheme {

        Surface(
            modifier =
                Modifier.fillMaxSize(),

            color =
                MaterialTheme
                    .colorScheme
                    .background
        ) {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(24.dp),

                verticalArrangement =
                    Arrangement.spacedBy(16.dp)

            ) {


                Text(
                    text =
                        "Sistema de Registro de Estudiantes",

                    fontSize = 25.sp,

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium
                )


                HorizontalDivider()


                // MATRÍCULA

                OutlinedTextField(

                    value = matricula,

                    onValueChange = {
                        matricula = it
                    },

                    label = {
                        Text("Matrícula")
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                // NOMBRE

                OutlinedTextField(

                    value = nombre,

                    onValueChange = {
                        nombre = it
                    },

                    label = {
                        Text("Nombre completo")
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                // CARRERA - DROPDOWNMENU

                Text(
                    text = "Carrera",
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )


                ExposedDropdownMenuBox(

                    expanded = expanded,

                    onExpandedChange = {

                        expanded =
                            !expanded
                    }

                ) {

                    OutlinedTextField(

                        value = carrera,

                        onValueChange = {},

                        readOnly = true,

                        label = {
                            Text("Seleccionar carrera")
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded = expanded
                                )
                        },

                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )


                    ExposedDropdownMenu(

                        expanded = expanded,

                        onDismissRequest = {

                            expanded = false
                        }

                    ) {

                        carreras.forEach {
                                opcion ->

                            DropdownMenuItem(

                                text = {

                                    Text(opcion)
                                },

                                onClick = {

                                    carrera =
                                        opcion

                                    expanded =
                                        false
                                }
                            )
                        }
                    }
                }


                HorizontalDivider()


                // TURNO

                Text(
                    text = "Turno",
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )


                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    RadioButton(

                        selected =
                            turno == "Matutino",

                        onClick = {

                            turno =
                                "Matutino"
                        }
                    )


                    Text(
                        text = "Matutino"
                    )


                    Spacer(
                        modifier =
                            Modifier
                                .padding(
                                    horizontal = 10.dp
                                )
                    )


                    RadioButton(

                        selected =
                            turno == "Vespertino",

                        onClick = {

                            turno =
                                "Vespertino"
                        }
                    )


                    Text(
                        text = "Vespertino"
                    )
                }


                HorizontalDivider()


                // ESTATUS

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically,

                    horizontalArrangement =
                        Arrangement.SpaceBetween

                ) {

                    Column {

                        Text(
                            text = "Estatus",
                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )

                        Text(
                            text =
                                if (activo) {
                                    "Activo"
                                } else {
                                    "Inactivo"
                                }
                        )
                    }


                    Switch(

                        checked = activo,

                        onCheckedChange = {

                            activo = it
                        }
                    )
                }


                HorizontalDivider()


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                // GUARDAR Y CONTINUAR

                Button(

                    onClick = {

                        // Validación

                        if (
                            matricula.isBlank() ||
                            nombre.isBlank() ||
                            carrera ==
                            "Selecciona una carrera"
                        ) {

                            Toast.makeText(
                                context,
                                "Completa matrícula, nombre y carrera",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {


                            // Guardamos en SharedPreferences

                            preferencesManager
                                .saveStudent(

                                    matricula =
                                        matricula,

                                    nombre =
                                        nombre,

                                    carrera =
                                        carrera,

                                    turno =
                                        turno,

                                    activo =
                                        activo
                                )


                            // Intent explícito

                            val intent =
                                Intent(
                                    context,
                                    DetailActivity::class.java
                                ).apply {


                                    putExtra(
                                        DetailActivity
                                            .EXTRA_MATRICULA,
                                        matricula
                                    )


                                    putExtra(
                                        DetailActivity
                                            .EXTRA_NOMBRE,
                                        nombre
                                    )


                                    putExtra(
                                        DetailActivity
                                            .EXTRA_CARRERA,
                                        carrera
                                    )


                                    putExtra(
                                        DetailActivity
                                            .EXTRA_TURNO,
                                        turno
                                    )


                                    putExtra(
                                        DetailActivity
                                            .EXTRA_ACTIVO,
                                        activo
                                    )
                                }


                            context.startActivity(
                                intent
                            )
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth()

                ) {

                    Text(
                        "Guardar y continuar"
                    )
                }


                // BORRAR DATOS GUARDADOS

                OutlinedButton(

                    onClick = {

                        preferencesManager
                            .clearStudent()

                        matricula = ""
                        nombre = ""

                        carrera =
                            "Selecciona una carrera"

                        turno =
                            "Matutino"

                        activo = true


                        Toast.makeText(
                            context,
                            "Datos guardados eliminados",
                            Toast.LENGTH_SHORT
                        ).show()
                    },

                    modifier =
                        Modifier.fillMaxWidth()

                ) {

                    Text(
                        "Borrar datos guardados"
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}