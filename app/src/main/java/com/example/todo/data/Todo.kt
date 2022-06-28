package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//this represents a table in our database
@Entity
data class Todo(
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
    @PrimaryKey val id: Int? = null
)
