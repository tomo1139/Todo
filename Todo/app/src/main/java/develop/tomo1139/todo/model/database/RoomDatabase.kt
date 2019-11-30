package develop.tomo1139.todo.model.database

import android.content.Context
import androidx.room.Room

object RoomDatabase {
    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "todoItems"
        ).build()
    }
}