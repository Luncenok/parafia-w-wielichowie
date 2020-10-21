package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "informacje_table")
data class Informacje(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "body")
    val body: String
)