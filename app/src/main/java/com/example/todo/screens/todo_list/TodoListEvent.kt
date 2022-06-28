package com.example.todo.screens.todo_list

import com.example.todo.data.Todo

sealed class TodoListEvent{
    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo,val isDone: Boolean): TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnUndoDeleteTodo: TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
}
