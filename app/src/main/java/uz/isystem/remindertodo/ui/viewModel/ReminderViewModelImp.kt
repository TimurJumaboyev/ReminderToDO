package uz.isystem.remindertodo.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted
import uz.isystem.remindertodo.core.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderViewModelImp @Inject constructor(
    private val repo: MainRepo,
) : ViewModel(), ReminderViewModel {

    private val data = MutableLiveData<List<NotCompleted>>()
    private val dataCompleted = MutableLiveData<List<Completed>>()

    override fun getListNotCompleted() {
        data.postValue(repo.getListNotCompleted())
    }

    override fun getListCompleted() {
        dataCompleted.postValue(repo.getListCompleted())
    }

    override fun insertCompleted(reminder: Completed) {
        repo.insertCompleted(reminder)
    }

    override fun insertNotCompleted(reminder: NotCompleted) {
        repo.insertNotCompleted(reminder)
    }


    override fun update(reminder: NotCompleted) {
        repo.update(reminder)
    }

    override fun deleteNotCompleted(reminder: NotCompleted) {
        repo.deleteNotCompleted(reminder)
    }


    override fun deleteCompleted(reminder: Completed) {
        repo.deleteCompleted(reminder)
    }

    override fun getDataNotCompleted(): MutableLiveData<List<NotCompleted>> {
        return data
    }

    override fun getDataCompleted(): MutableLiveData<List<Completed>> {
        return dataCompleted
    }
}