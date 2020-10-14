package codes.idziejczak.parafiawwielichowie

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import codes.idziejczak.parafiawwielichowie.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        }
    }

    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(8, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}