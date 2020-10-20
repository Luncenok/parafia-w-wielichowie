package codes.idziejczak.parafiawwielichowie.repository

import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.Grupy
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GrupyRepository(private val database: AppDatabase) {

    val grupy = database.grupyDatabaseDao.getGrupyLiveData()

    suspend fun refreshGrupy() {
        withContext(Dispatchers.IO) {
            val documentResult = ParafiaApi.retrofitService.getGrupy()
            val stringSpanned =
                documentResult.select("[style=\"width:890px; margin:auto;  padding-left:45px; padding-right:40px;\"]")
                    .toString()
                    .replace(
                        "<a href=\"wydrukuj-22.html\"><img style=\"border: 0px;\" alt=\"Wydrukuj tekst...\" src=\"css/print.gif\"></a>",
                        ""
                    )
                    .replace("</p>", "</p><br>")
            database.grupyDatabaseDao.insert(
                Grupy(
                    id = 1,
                    body = stringSpanned
                )
            )
        }
    }

    fun getGrupy(): Grupy {
        return database.grupyDatabaseDao.getGrupy()
    }

}