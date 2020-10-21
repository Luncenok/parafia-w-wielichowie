package codes.idziejczak.parafiawwielichowie.work

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.*
import codes.idziejczak.parafiawwielichowie.ui.MainActivity
import retrofit2.HttpException
import kotlin.random.Random

class RefreshDataWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val WORK_NAME = "codes.idziejczak.parafiawwielichowie.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AppDatabase.getInstance(applicationContext)
        val ogloszeniaRepository = OgloszeniaRepository(database)
        val grupyRepository = GrupyRepository(database)
        val kalendariumRepository = KalendariumRepository(database)
        val niezbednikiRepository = NiezbednikiRepository(database)
        val informacjeRepository = InformacjeRepository(database)
        val kontaktRepository = KontaktRepository(database)

        try {
            ogloszeniaRepository.refreshOglosznenia()
            grupyRepository.refreshGrupy()
            kalendariumRepository.refreshKalendarium()
            niezbednikiRepository.refreshNiezbedniki()
            informacjeRepository.refreshInformacje()
            kontaktRepository.refreshKontakt()
            ogloszeniaRepository.getgloszenia().let {
                if (it.first().notify) {
                    val notificationManager = ContextCompat.getSystemService(
                        applicationContext,
                        NotificationManager::class.java
                    ) as NotificationManager
                    notificationManager.notify(
                        Random.nextInt(Int.MAX_VALUE), getNotificationBuilder()
                            .setContentTitle(applicationContext.getString(R.string.notification_ogloszenie_title))
                            .setContentText(applicationContext.getString(R.string.notification_ogloszenie_text))
                            .build()
                    )
                }
            }
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }


    private fun getNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, "ogloszenia_channel")
            .setSmallIcon(R.drawable.ic_sun)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            .setContentIntent(
                NavDeepLinkBuilder(applicationContext)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.navigation)
                    .setDestination(R.id.ogloszeniaFragment)
                    .createPendingIntent()
            )
    }

}