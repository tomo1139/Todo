package develop.tomo1139.todo.view

import develop.tomo1139.todo.model.data.TodoItem

interface MainControllerCallback {
    fun onClickDelete(todoItem: TodoItem)
    fun onClickCheck(todoItem: TodoItem)
}