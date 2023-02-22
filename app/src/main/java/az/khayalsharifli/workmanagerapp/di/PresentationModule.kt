package az.khayalsharifli.workmanagerapp.di

import az.khayalsharifli.workmanagerapp.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MainViewModel(
            downloadRequest = get(named("download_image_worker")),
            notificationRequest = get(named("notification_worker")),
            workManager = get()
        )
    }
}