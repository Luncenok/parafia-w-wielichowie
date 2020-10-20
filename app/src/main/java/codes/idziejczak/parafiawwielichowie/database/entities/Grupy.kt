package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grupy_table")
data class Grupy(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "body")
    val body: String
)