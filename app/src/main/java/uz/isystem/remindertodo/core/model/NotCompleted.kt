package uz.isystem.remindertodo.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "notCompleted")
class NotCompleted(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val description: String,
    @ColumnInfo(name = "date")
    var date: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}