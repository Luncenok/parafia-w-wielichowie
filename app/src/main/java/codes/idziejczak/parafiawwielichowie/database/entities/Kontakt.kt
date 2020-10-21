package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kontakt_table")
data class Kontakt(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "body")
    val body: String
)