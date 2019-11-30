package develop.tomo1139.todo.view

import com.airbnb.epoxy.TypedEpoxyController
import develop.tomo1139.todo.TodoCellBindingModel_
import develop.tomo1139.todo.model.data.TodoItem

class MainController(private val callback: MainControllerCallback) : TypedEpoxyController<List<TodoItem>>() {
    override fun buildModels(datas: List<TodoItem>) {
        datas.reversed().forEach {
            TodoCellBindingModel_()
                .id(it.id)
                .callback(callback)
                .todoItem(it)
                .addTo(this)
        }
    }
}