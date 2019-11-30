package develop.tomo1139.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import develop.tomo1139.todo.model.data.TodoItem
import develop.tomo1139.todo.model.database.RoomDatabase
import develop.tomo1139.todo.view.MainController
import develop.tomo1139.todo.view.MainControllerCallback
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope, MainControllerCallback {
    val controller = MainController(this)
    val todoTitleEditText = MutableLiveData<String>()
    val itemLeftText = MutableLiveData<String>()
    val selectedCondition = MutableLiveData<SelectedCondition>().also {
        it.value = SelectedCondition.All
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = Job()
    private var todoItems = listOf<TodoItem>()
    private val dao by lazy { RoomDatabase.db.todoItemDao() }

    fun loadTodoItems() = launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { todoItems = dao.getAll() }
        val leftItemCount = todoItems.count { !it.completed }
        itemLeftText.value = "$leftItemCount items left"

        when (selectedCondition.value) {
            SelectedCondition.All -> Unit
            SelectedCondition.Active -> todoItems = todoItems.filter { !it.completed }
            SelectedCondition.Completed -> todoItems = todoItems.filter { it.completed }
        }
        controller.setData(todoItems)
    }

    fun addTodoItem() = launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { dao.insert(TodoItem(title = todoTitleEditText.value.toString())) }
        loadTodoItems()
        todoTitleEditText.value = ""
    }

    fun clearCompleted() = launch(Dispatchers.Main) {
        todoItems.forEach {
            if (it.completed) {
                withContext(Dispatchers.IO) { dao.update(it.copy(completed = false)) }
            }
        }
        loadTodoItems()
    }

    fun setCondition(selectedCondition: SelectedCondition) {
        this.selectedCondition.value = selectedCondition
        loadTodoItems()
    }

    override fun onClickDelete(todoItem: TodoItem) {
        removeTodoItem(todoItem)
    }

    override fun onClickCheck(todoItem: TodoItem) {
        toggleCheck(todoItem)
    }

    private fun removeTodoItem(todoItem: TodoItem) = launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { dao.delete(todoItem) }
        loadTodoItems()
    }

    private fun toggleCheck(todoItem: TodoItem) = launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { dao.update(todoItem.copy(completed = !todoItem.completed)) }
        loadTodoItems()
    }

    enum class SelectedCondition {
        All,
        Active,
        Completed,
    }
}