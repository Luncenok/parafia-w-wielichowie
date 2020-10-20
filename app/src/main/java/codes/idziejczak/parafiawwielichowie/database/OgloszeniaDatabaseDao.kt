package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.idziejczak.parafiawwielichowie.database.entities.Ogloszenie

@Dao
interface OgloszeniaDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ogloszenie: Ogloszenie)

    @Query("SELECT * FROM ogloszenia_table ORDER BY id DESC")
    fun getAllOgloszeniaLiveData(): LiveData<List<Ogloszenie>>

    @Query("SELECT * FROM ogloszenia_table ORDER BY id DESC")
    fun getAllOgloszenia(): List<Ogloszenie>

    @Update
    suspend fun update(ogloszenie: Ogloszenie)

    @Query("DELETE FROM ogloszenia_table")
    suspend fun clear()
}