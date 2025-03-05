package org.todolist.bakapp

// TodoScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {
    var newTaskText by remember { mutableStateOf("") }
    val tasks by todoViewModel.tasks.collectAsState()

    // Estado para controlar la tarea seleccionada para eliminación
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    // Estado para controlar la visibilidad del BottomSheet
    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = newTaskText,
            onValueChange = { newTaskText = it },
            label = { Text("Nueva Tarea") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black, // Color del texto cuando está enfocado
                unfocusedTextColor = Color.Gray, // Color del texto cuando no está enfocado
                focusedContainerColor = Color.White, // Fondo del contenedor cuando está enfocado
                unfocusedContainerColor = Color(0xFFF1F1F1), // Fondo del contenedor cuando no está enfocado
                focusedIndicatorColor = Color.Blue, // Color del borde cuando está enfocado
                unfocusedIndicatorColor = Color.Gray, // Color del borde cuando no está enfocado
                cursorColor = Color.Blue, // Color del cursor
                focusedLabelColor = Color.Blue, // Color de la etiqueta cuando está enfocada
                unfocusedLabelColor = Color.Gray // Color de la etiqueta cuando no está enfocada
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                todoViewModel.addTask(newTaskText)
                "".also { newTaskText = it }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            enabled = newTaskText.length >= 3
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar",style = MaterialTheme.typography.titleLarge, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onDelete = {
                        taskToDelete = task
                        isSheetOpen = true
                    }
                )
            }
        }
        // ModalBottomSheet para confirmar eliminación
        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Estás seguro de que deseas eliminar esta tarea?",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            taskToDelete?.let {
                                todoViewModel.deleteTask(it)
                            }
                            isSheetOpen = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Eliminar", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { isSheetOpen = false }) {
                        Text("Cancelar")
                    }
                }
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