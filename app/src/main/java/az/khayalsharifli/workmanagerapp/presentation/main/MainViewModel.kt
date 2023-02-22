package az.khayalsharifli.workmanagerapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import az.khayalsharifli.workmanagerapp.data.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MainViewModel(
     val downloadRequest: OneTimeWorkRequest,
     val notificationRequest: OneTimeWorkRequest,
     val workManager: WorkManager
) : ViewModel() {

    fun startWorkers() {
        workManager
            .beginUniqueWork(
                "download",
                ExistingWorkPolicy.KEEP,
                downloadRequest
            )
            .then(notificationRequest)
            .enqueue()
    }
}