package com.example.todo.screens.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Todo
import com.example.todo.data.TodoRepository
import com.example.todo.util.Routes
import com.example.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject  constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //this is used to cache the deleted to_do
    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent){
        when (event){
            is TodoListEvent.OnTodoClick->{
                sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id }"))
            }
            is TodoListEvent.OnAddTodoClick ->{
                sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDeleteTodoClick ->{
                viewModelScope.launch {
                    //cache the deleted to_do
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    //show user snackbar on deletion
                    sendUiEvents(UiEvent.ShowSnackbar(
                        message = "todo deleted",
                        action = "undo"
                    ))
                }
            }
            is TodoListEvent.OnUndoDeleteTodo->{
                //check that deleted to do is not null
                deletedTodo.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo!!)
                    }
                }
            }
            is TodoListEvent.OnDoneChange->{
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(isDone = event.isDone)
                    )
                }
            }
        }
    }

    private fun sendUiEvents(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}