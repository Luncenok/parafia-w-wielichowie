package codes.idziejczak.parafiawwielichowie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ogloszenie::class, Grupy::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val ogloszeniaDatabaseDao: OgloszeniaDatabaseDao
    abstract val grupyDatabaseDao: GrupyDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}