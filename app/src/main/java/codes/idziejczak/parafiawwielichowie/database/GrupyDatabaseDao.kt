package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Grupy

@Dao
interface GrupyDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grupy: Grupy)

    @Query("SELECT * FROM grupy_table LIMIT 1")
    fun getGrupy(): Grupy

    @Query("SELECT * FROM grupy_table LIMIT 1")
    fun getGrupyLiveData(): LiveData<Grupy>

    @Update
    fun update(grupy: Grupy)

}