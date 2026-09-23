package com.example.practica01

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(mensaje: String, onBackClick: () -> Unit){
    Scaffold( containerColor = Color(0xFFF3E5F5), topBar = {
        TopAppBar(title = {Text("Segunda pantalla")},
            navigationIcon = { IconButton(onClick = {onBackClick()})  {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar") }})
    }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Texto recibido:", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensaje, fontSize = 24.sp)
        }
    }
}