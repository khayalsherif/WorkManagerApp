package az.khayalsharifli.workmanagerapp.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import az.khayalsharifli.workmanagerapp.presentation.worker.DownloadImageWorker
import az.khayalsharifli.workmanagerapp.presentation.worker.NotificationWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module

val workerModule = module {
    worker { DownloadImageWorker(get(), get()) }
    worker { NotificationWorker(get(), get()) }

    factory(named("download_image_worker")) {
        OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .build()
    }

    factory(named("notification_worker")) {
        OneTimeWorkRequestBuilder<NotificationWorker>()
            .build()
    }

    single { WorkManager.getInstance(get()) }
}