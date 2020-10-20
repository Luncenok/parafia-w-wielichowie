package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "niezbedniki_table")
data class Niezbedniki(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "narzeczeniichrzestni")
    val narzeczeniIChrzestni: String,

    @ColumnInfo(name = "zmarli")
    val zmarli: String
)