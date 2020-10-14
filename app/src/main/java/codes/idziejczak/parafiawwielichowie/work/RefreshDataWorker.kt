package codes.idziejczak.parafiawwielichowie.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.OgloszeniaRepository
import retrofit2.HttpException

class RefreshDataWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val WORK_NAME = "codes.idziejczak.parafiawwielichowie.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AppDatabase.getInstance(applicationContext)
        val repository = OgloszeniaRepository(database)

        try {
            repository.refreshOglosznenia()
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

}