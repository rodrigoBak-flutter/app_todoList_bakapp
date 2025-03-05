package org.todolist.bakapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform