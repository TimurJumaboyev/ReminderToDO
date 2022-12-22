package uz.isystem.remindertodo.core.repository

import uz.isystem.remindertodo.core.db.DataBase
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted
import javax.inject.Inject

class MainRepoImp @Inject constructor(
    private val db: DataBase,
) : MainRepo {
    override fun getListNotCompleted(): List<NotCompleted> {
        return db.getDao().getNotCompletedList()
    }

    override fun getListCompleted(): List<Completed> {
        return db.getDao().getCompletedList()
    }

    override fun insertCompleted(reminder: Completed) {
        db.getDao().insertCompletedReminder(reminder)
    }

    override fun insertNotCompleted(reminder: NotCompleted) {
        db.getDao().insertNotCompletedReminder(reminder)
    }

    override fun update(reminder: NotCompleted) {
        db.getDao().updateNotCompletedReminder(reminder)
    }

    override fun deleteNotCompleted(reminder: NotCompleted) {
        db.getDao().deleteNotCompletedWord(reminder)
    }

    override fun deleteCompleted(reminder: Completed) {
        db.getDao().deleteCompletedWord(reminder)
    }


}