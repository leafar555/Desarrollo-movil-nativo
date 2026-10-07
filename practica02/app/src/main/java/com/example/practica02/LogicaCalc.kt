package com.example.practica02

object LogicaCalc {

    fun calcular(
        num1: String,
        num2: String,
        operation: String
    ): String {

        // Evita errores por datos vacíos o inválidos
        val n1 =
            num1.toDoubleOrNull()
                ?: return "Error"

        val n2 =
            num2.toDoubleOrNull()
                ?: return "Error"


        val result = when (operation) {

            "+" -> n1 + n2

            "-" -> n1 - n2

            "x" -> n1 * n2

            "÷" -> {

                // Evita división entre cero
                if (n2 != 0.0) {

                    n1 / n2

                } else {

                    return "Error"
                }
            }

            else -> return "Error"
        }


        // Si el resultado es entero, no muestra .0
        return if (result % 1.0 == 0.0) {

            result.toLong().toString()

        } else {

            result.toString()
        }
    }


    fun calcularPorcentaje(
        num: String
    ): String {

        val n =
            num.toDoubleOrNull()
                ?: return "Error"


        val result =
            n / 100


        return if (result % 1.0 == 0.0) {

            result.toLong().toString()

        } else {

            result.toString()
        }
    }
}