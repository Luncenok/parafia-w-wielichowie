package codes.idziejczak.parafiawwielichowie.repository

import androidx.lifecycle.LiveData
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.Ogloszenie
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OgloszeniaRepository(private val database: AppDatabase) {

    val ogloszeniaList: LiveData<List<Ogloszenie>> =
        database.ogloszeniaDatabaseDao.getAllOgloszenia()

    suspend fun refreshOglosznenia() {
        withContext(Dispatchers.Default) {
            val documentResult = ParafiaApi.retrofitService.getIdOgloszenia()
            val idLast =
                documentResult.select("[style=\"float: left; \"] a:first-of-type").attr("href")
                    .replace(
                        "ogloszenia,parafialne,", ""
                    ).replace(".html", "").toInt()
            for (i in idLast downTo idLast - 10) {
                val documentResult2 = ParafiaApi.retrofitService.getOgloszenie(i.toString())
                val stringSpanned =
                    documentResult2.select("[style=\"text-align:justify; padding-right:25px\"]")
                        .toString()
                database.ogloszeniaDatabaseDao.insert(
                    Ogloszenie(
                        id = i,
                        body = stringSpanned
                    )
                )
            }
        }
    }

}
