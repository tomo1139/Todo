package develop.tomo1139.todo.model.data

import androidx.room.*

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todoItem")
    fun getAll(): List<TodoItem>

    @Insert
    fun insert(todoItem: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Update
    fun update(todoItem: TodoItem)
}