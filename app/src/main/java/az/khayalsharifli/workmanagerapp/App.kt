package az.khayalsharifli.workmanagerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import az.khayalsharifli.workmanagerapp.di.dataModule
import az.khayalsharifli.workmanagerapp.di.presentationModule
import az.khayalsharifli.workmanagerapp.di.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notification_channel",
                "File download",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        startKoin {
            properties(
                mapOf("base" to "https://images.unsplash.com/")
            )
            androidContext(this@App)
            val modules = listOf(dataModule, presentationModule, workerModule)
            modules(modules = modules)
        }
    }
}