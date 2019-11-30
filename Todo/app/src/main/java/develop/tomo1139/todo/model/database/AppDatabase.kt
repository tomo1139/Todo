package develop.tomo1139.todo.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import develop.tomo1139.todo.model.data.TodoItem
import develop.tomo1139.todo.model.data.TodoItemDao

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
}