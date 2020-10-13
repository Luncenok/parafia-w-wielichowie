package codes.idziejczak.parafiawwielichowie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ogloszenia_table")
data class Ogloszenie(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "body")
    val body: String
)