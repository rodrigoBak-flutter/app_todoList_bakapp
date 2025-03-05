package org.todolist.bakapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa Firebase
        FirebaseApp.initializeApp(this)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        TodoScreen()
    }
}
