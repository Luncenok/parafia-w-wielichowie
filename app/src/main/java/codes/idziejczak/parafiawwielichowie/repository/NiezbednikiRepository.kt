package codes.idziejczak.parafiawwielichowie.repository

import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.Kalendarium
import codes.idziejczak.parafiawwielichowie.database.entities.Niezbedniki
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NiezbednikiRepository(private val database: AppDatabase) {

    val niezbedniki = database.niezbednikiDatabaseDao.getNiezbednikiLiveData()

    suspend fun refreshNiezbedniki() {
        withContext(Dispatchers.IO) {
            val documentResult = ParafiaApi.retrofitService.getSakramenty()
            val stringSpanned1 =
                documentResult.select("[style=\"width:890px; margin:auto;  padding-left:45px; padding-right:40px;\"]")
                    .first().toString()
                    .replace(
                        "<a href=\"wydrukuj-14.html\"><img style=\"border: 0px;\" alt=\"Wydrukuj tekst...\" src=\"css/print.gif\"></a>",
                        " "
                    )
            val documentResult2 = ParafiaApi.retrofitService.getPorady()
            val stringSpanned2 =
                documentResult2.select("[style=\"width:890px; margin:auto;  padding-left:45px; padding-right:40px;\"]")
                    .first().toString()
                    .replace(
                        "<a href=\"wydrukuj-6.html\"><img style=\"border: 0px;\" alt=\"Wydrukuj tekst...\" src=\"css/print.gif\"></a>",
                        " "
                    )
            database.niezbednikiDatabaseDao.insert(
                Niezbedniki(
                    id = 1,
                    narzeczeniIChrzestni = stringSpanned1,
                    zmarli = stringSpanned2
                )
            )
        }
    }

    fun getKalendarium(): Kalendarium {
        return database.kalendariumDatabaseDao.getKalendarium()
    }

}