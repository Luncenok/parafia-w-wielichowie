package codes.idziejczak.parafiawwielichowie.repository

import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.database.entities.Kalendarium
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KalendariumRepository(private val database: AppDatabase) {

    val kalendarium = database.kalendariumDatabaseDao.getKalendariumLiveData()

    suspend fun refreshKalendarium() {
        withContext(Dispatchers.IO) {
            val documentResult = ParafiaApi.retrofitService.getKalendarium()
            val stringSpanned1 =
                "<h3>Rocznica śmierci</h3>" + documentResult.select(".wykaz").first().toString()
                    .replace(
                        "<img style=\"border:0px;\" src=\"images/zobacz.gif\" width=\"17\" height=\"27\" alt=\"zobacz\">",
                        ""
                    )
                    .replace("<td>&nbsp;&nbsp;</td>", "")
                    .replace("<td ", "<span ")
                    .replace("<td>", "<span>&nbsp;&nbsp;</span><span>")
                    .replace("</td>", "</span>")
                    .replace("<tr>", "<span>")
                    .replace("</tr>", "</span><br>")
                    .replace("<a href=\"", "<span><a href=\"https://parafiawielichowo.pl/")
                    .replace("<span class=\"g\" width=\"50px\"><b>zobacz</b></span>", "")
            val stringSpanned2 =
                "<h3>Rocznica urodzin</h3>" + documentResult.select(".wykaz").last().toString()
                    .replace(
                        "<img style=\"border:0px;\" src=\"images/zobacz.gif\" width=\"17\" height=\"27\" alt=\"zobacz\">",
                        ""
                    )
                    .replace("<td>&nbsp;&nbsp;</td>", "")
                    .replace("<td ", "<span ")
                    .replace("<td>", "<span>&nbsp;&nbsp;</span><span>")
                    .replace("</td>", "</span>")
                    .replace("<tr>", "<span>")
                    .replace("</tr>", "</span><br>")
                    .replace("<a href=\"", "<span><a href=\"https://parafiawielichowo.pl/")
                    .replace("<span class=\"g\" width=\"50px\"><b>zobacz</b></span>", "")
            database.kalendariumDatabaseDao.insert(
                Kalendarium(
                    id = 1,
                    roczSmierci = stringSpanned1,
                    roczUrodzin = stringSpanned2
                )
            )
        }
    }

    fun getKalendarium(): Kalendarium {
        return database.kalendariumDatabaseDao.getKalendarium()
    }

}