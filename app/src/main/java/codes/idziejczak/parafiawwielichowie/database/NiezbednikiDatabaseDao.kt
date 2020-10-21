package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Niezbedniki

@Dao
interface NiezbednikiDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(niezbedniki: Niezbedniki)

    @Query("SELECT * FROM niezbedniki_table LIMIT 1")
    fun getNiezbedniki(): Niezbedniki

    @Query("SELECT * FROM niezbedniki_table LIMIT 1")
    fun getNiezbednikiLiveData(): LiveData<Niezbedniki>

    @Update
    fun update(niezbedniki: Niezbedniki)
}