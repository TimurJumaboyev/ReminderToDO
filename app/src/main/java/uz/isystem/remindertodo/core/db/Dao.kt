package uz.isystem.remindertodo.core.db

import androidx.room.*
import androidx.room.Dao
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted


@Dao
interface Dao {

    @Query("SELECT * FROM completed")
    fun getCompletedList(): List<Completed>

    @Query("SELECT * FROM notCompleted")
    fun getNotCompletedList(): List<NotCompleted>

    @Insert
    fun insertCompletedReminder(reminder: Completed)

    @Insert
    fun insertNotCompletedReminder(reminder: NotCompleted)

    @Delete
    fun deleteCompletedWord(reminder: Completed)

    @Delete
    fun deleteNotCompletedWord(reminder: NotCompleted)

    @Update
    fun updateCompletedReminder(reminder: NotCompleted)

    @Update
    fun updateNotCompletedReminder(reminder: NotCompleted)


}