package codes.idziejczak.parafiawwielichowie.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OgloszeniaDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ogloszenie: Ogloszenie)

    @Query("SELECT * FROM ogloszenia_table")
    fun getAllOgloszenia(): LiveData<List<Ogloszenie>>

    @Update
    suspend fun update(ogloszenie: Ogloszenie)

    @Query("DELETE FROM ogloszenia_table")
    suspend fun clear()
}