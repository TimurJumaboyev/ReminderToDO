package uz.isystem.remindertodo.core.repository

import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.model.NotCompleted

interface MainRepo {

    fun getListNotCompleted(): List<NotCompleted>
    fun getListCompleted(): List<Completed>
    fun insertCompleted(reminder: Completed)
    fun insertNotCompleted(reminder: NotCompleted)
    fun update(reminder: NotCompleted)
    fun deleteNotCompleted(reminder: NotCompleted)
    fun deleteCompleted(reminder: Completed)

}