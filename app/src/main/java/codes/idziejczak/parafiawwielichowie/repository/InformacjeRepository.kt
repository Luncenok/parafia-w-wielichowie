package codes.idziejczak.parafiawwielichowie.repository

import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.Informacje
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InformacjeRepository(private val database: AppDatabase) {
    val informacje = database.informacjeDatabaseDao.getInformacjeLiveData()

    suspend fun refreshInformacje() {
        withContext(Dispatchers.IO) {
            val documentResult = ParafiaApi.retrofitService.getInformacje()
            val stringSpanned =
                documentResult.select("[style=\"padding-left:6px; padding-top:7px; padding-right:6px; width:165px\"]")
                    .toString()
            database.informacjeDatabaseDao.insert(
                Informacje(
                    id = 1,
                    body = stringSpanned
                )
            )
        }
    }

    fun getInformacje(): Informacje {
        return database.informacjeDatabaseDao.getInformacje()
    }
}