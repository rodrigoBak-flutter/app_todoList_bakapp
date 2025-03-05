package org.todolist.bakapp

// TodoViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    // Estado observable de la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        // Escucha los cambios de Firebase y actualiza el estado
        repository.getTasks { fetchedTasks ->
            _tasks.value = fetchedTasks
        }
    }

    fun addTask(text: String) {
        if (text.isNotBlank()) {
            repository.addTask(Task(text = text)) { success ->
                if (!success) {
                    // Maneja el error, si es necesario
                }
            }
        }
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task) { success ->
            if (!success) {
                // Maneja el error, si es necesario
            }
        }
    }
}