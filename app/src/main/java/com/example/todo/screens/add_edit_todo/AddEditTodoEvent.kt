package com.example.todo.screens.add_edit_todo

sealed class AddEditTodoEvent{
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    object OnSavedTodoClick: AddEditTodoEvent()
    object OnPopBackStack: AddEditTodoEvent()
}
