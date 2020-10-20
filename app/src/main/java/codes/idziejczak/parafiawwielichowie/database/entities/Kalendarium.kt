package codes.idziejczak.parafiawwielichowie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kalendarium_table")
data class Kalendarium(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "roczSmierci")
    val roczSmierci: String,

    @ColumnInfo(name = "roczUrodzin")
    val roczUrodzin: String
)