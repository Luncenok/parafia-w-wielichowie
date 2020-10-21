package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Informacje

@Dao
interface InformacjeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(informacje: Informacje)

    @Query("SELECT * FROM informacje_table LIMIT 1")
    fun getInformacje(): Informacje

    @Query("SELECT * FROM informacje_table LIMIT 1")
    fun getInformacjeLiveData(): LiveData<Informacje>

    @Update
    fun update(informacje: Informacje)
}