package codes.idziejczak.parafiawwielichowie

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import codes.idziejczak.parafiawwielichowie.work.RefreshDataWorker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ParafiaApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
            Timber.plant(Timber.DebugTree())
            createChannel()
            setupFirebase()
        }
    }

    private fun setupFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag("loggo").w("Fetching FCM registration token failed\n${task.exception}")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Timber.tag("loggo").d(msg)
        })
    }

    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(3, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "ogloszenia_channel",
                "Ogłoszenia",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.description =
                applicationContext.getString(R.string.notification_ogloszenie_title)

            val notificationChannel2 = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                "Powiadomienia Push",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel2.enableVibration(true)
            notificationChannel2.enableLights(true)
            notificationChannel2.description = "Powiadomienia od twórcy"

            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.createNotificationChannel(notificationChannel2)
        }
    }
}