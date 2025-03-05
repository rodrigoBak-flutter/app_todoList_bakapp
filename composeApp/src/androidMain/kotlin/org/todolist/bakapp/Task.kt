package org.todolist.bakapp

data class Task(
    val id: String? = null,
    val text: String = "",
    val isCompleted: Boolean = false
)