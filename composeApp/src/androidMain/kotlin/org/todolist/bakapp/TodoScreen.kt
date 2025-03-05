package org.todolist.bakapp

// TodoScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {
    var newTaskText by remember { mutableStateOf("") }
    val tasks by todoViewModel.tasks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = newTaskText,
            onValueChange = { newTaskText = it },
            label = { Text("Nueva Tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                todoViewModel.addTask(newTaskText)
                "".also { newTaskText = it }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task, onDelete = { todoViewModel.deleteTask(task) })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    // Cada tarea se muestra con su texto y un botón para eliminarla
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = task.text, modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar"
            )
        }
    }
}