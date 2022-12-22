package uz.isystem.remindertodo.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "remind")
class ReminderTableModel(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val description: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "isComplated")
    val isComplated: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}