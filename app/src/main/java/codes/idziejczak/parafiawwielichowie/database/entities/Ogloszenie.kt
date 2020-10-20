package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ogloszenia_table")
data class Ogloszenie(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "notify")
    val notify: Boolean,

    @ColumnInfo(name = "body")
    val body: String
)