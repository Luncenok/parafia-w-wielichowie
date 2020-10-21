package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Kontakt

@Dao
interface KontaktDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kontakt: Kontakt)

    @Query("SELECT * FROM kontakt_table LIMIT 1")
    fun getKontakt(): Kontakt

    @Query("SELECT * FROM kontakt_table LIMIT 1")
    fun getKontaktLiveData(): LiveData<Kontakt>

    @Update
    fun update(kontakt: Kontakt)
}