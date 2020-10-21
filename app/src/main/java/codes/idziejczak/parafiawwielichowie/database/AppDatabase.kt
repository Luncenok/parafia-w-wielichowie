package codes.idziejczak.parafiawwielichowie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.*

@Database(
    entities = [
        Ogloszenie::class,
        Grupy::class,
        Kalendarium::class,
        Niezbedniki::class,
        Informacje::class,
        Kontakt::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val ogloszeniaDatabaseDao: OgloszeniaDatabaseDao
    abstract val grupyDatabaseDao: GrupyDatabaseDao
    abstract val kalendariumDatabaseDao: KalendariumDatabaseDao
    abstract val niezbednikiDatabaseDao: NiezbednikiDatabaseDao
    abstract val informacjeDatabaseDao: InformacjeDatabaseDao
    abstract val kontaktDatabaseDao: KontaktDatabaseDao

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