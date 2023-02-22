package az.khayalsharifli.workmanagerapp.presentation.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import az.khayalsharifli.workmanagerapp.R
import kotlinx.coroutines.delay
import kotlin.random.Random

class NotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        startForegroundService()
        delay(60000)
        return Result.success()
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "notification_channel")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText("Worker 2")
                    .setContentTitle("Notification showing")
                    .build()
            )
        )
    }
}