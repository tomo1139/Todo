package develop.tomo1139.todo

import android.app.Application
import develop.tomo1139.todo.model.database.RoomDatabase

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RoomDatabase.init(this)
    }
}