package uz.isystem.remindertodo.ui.viewModel

import androidx.lifecycle.MutableLiveData
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted

interface ReminderViewModel {

    fun getListNotCompleted()
    fun getListCompleted()
    fun insertCompleted(reminder: Completed)
    fun insertNotCompleted(reminder: NotCompleted)
    fun update(reminder: NotCompleted)
    fun deleteNotCompleted(reminder: NotCompleted)
    fun deleteCompleted(reminder: Completed)
    fun getDataNotCompleted(): MutableLiveData<List<NotCompleted>>
    fun getDataCompleted(): MutableLiveData<List<Completed>>

}