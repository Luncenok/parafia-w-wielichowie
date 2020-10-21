package codes.idziejczak.parafiawwielichowie.repository

import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.Kontakt
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KontaktRepository(private val database: AppDatabase) {
    val kontakt = database.kontaktDatabaseDao.getKontaktLiveData()

    suspend fun refreshKontakt() {
        withContext(Dispatchers.IO) {
            val documentResult = ParafiaApi.retrofitService.getKontakt()
            val stringSpanned =
                documentResult.select("[style=\"width:890px; margin:auto;  padding-left:45px; padding-right:40px;\"]")
                    .toString()
                    .replace(
                        "<a href=\"wydrukuj-7.html\"><img style=\"border: 0px;\" alt=\"Wydrukuj tekst...\" src=\"css/print.gif\"></a>",
                        ""
                    )
                    .replace("../", "https://parafiawielichowo.pl/")
            database.kontaktDatabaseDao.insert(
                Kontakt(
                    id = 1,
                    body = stringSpanned
                )
            )
        }
    }

    fun getKontakt(): Kontakt {
        return database.kontaktDatabaseDao.getKontakt()
    }
}