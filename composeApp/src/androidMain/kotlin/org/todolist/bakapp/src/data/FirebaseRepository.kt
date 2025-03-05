package org.todolist.bakapp

// FirebaseRepository.kt
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class FirebaseRepository {

    private val databaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("tasks")

    // Agrega una tarea a Firebase
    fun addTask(task: Task, onComplete: (Boolean) -> Unit) {
        val key = databaseRef.push().key
        if (key != null) {
            val taskWithId = task.copy(id = key)
            databaseRef.child(key).setValue(taskWithId)
                .addOnCompleteListener { onComplete(it.isSuccessful) }
        } else {
            onComplete(false)
        }
    }

    // Elimina una tarea de Firebase
    fun deleteTask(task: Task, onComplete: (Boolean) -> Unit) {
        task.id?.let { id ->
            databaseRef.child(id).removeValue()
                .addOnCompleteListener { onComplete(it.isSuccessful) }
        } ?: onComplete(false)
    }

    // Escucha los cambios en la base de datos y entrega la lista de tareas
    fun getTasks(onTasksChanged: (List<Task>) -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = snapshot.children.mapNotNull { it.getValue(Task::class.java) }
                onTasksChanged(tasks)
            }
            override fun onCancelled(error: DatabaseError) {
                // Maneja el error según sea necesario
            }
        })
    }
}