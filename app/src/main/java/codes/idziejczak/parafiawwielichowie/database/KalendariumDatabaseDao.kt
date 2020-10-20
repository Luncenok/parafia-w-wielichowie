package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Kalendarium

@Dao
interface KalendariumDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kalendarium: Kalendarium)

    @Query("SELECT * FROM kalendarium_table LIMIT 1")
    fun getKalendarium(): Kalendarium

    @Query("SELECT * FROM kalendarium_table LIMIT 1")
    fun getKalendariumLiveData(): LiveData<Kalendarium>

    @Update
    fun update(kalendarium: Kalendarium)
}