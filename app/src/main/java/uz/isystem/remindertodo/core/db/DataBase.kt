package uz.isystem.remindertodo.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted

@Database(entities = [NotCompleted::class, Completed::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun getDao(): Dao




}
