package az.khayalsharifli.workmanagerapp.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.work.*
import az.khayalsharifli.workmanagerapp.presentation.worker.WorkerKeys
import az.khayalsharifli.workmanagerapp.ui.theme.WorkManagerAppTheme
import coil.compose.rememberImagePainter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerAppTheme {
                val context = LocalContext.current
                var hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else mutableStateOf(true)
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasNotificationPermission = isGranted
                    }
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    LaunchedEffect(key1 = true) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
                if (hasNotificationPermission) {
                    MainConcept()
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Give me notification access in setting")
                    }
                }
            }
        }
    }

    @Composable
    fun MainConcept() {
        val workInfos = viewModel.workManager
            .getWorkInfosForUniqueWorkLiveData("download")
            .observeAsState()
            .value

        val downloadInfo = remember(key1 = workInfos) {
            workInfos?.find { it.id == viewModel.downloadRequest.id }
        }

        val notificationInfo = remember(key1 = workInfos) {
            workInfos?.find { it.id == viewModel.notificationRequest.id }
        }

        val imageUri by derivedStateOf {
            downloadInfo?.outputData?.getString(WorkerKeys.IMAGE_URI)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imageUri?.let { uri ->
                Image(
                    painter = rememberImagePainter(
                        data = uri
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    viewModel.startWorkers()
                },
                enabled = downloadInfo?.state != WorkInfo.State.RUNNING
            ) {
                Text(text = "Start download")
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (downloadInfo?.state) {
                WorkInfo.State.RUNNING -> Text("Downloading...")
                WorkInfo.State.SUCCEEDED -> Text("Download succeeded")
                WorkInfo.State.FAILED -> Text("Download failed")
                WorkInfo.State.CANCELLED -> Text("Download cancelled")
                WorkInfo.State.ENQUEUED -> Text("Download enqueued")
                WorkInfo.State.BLOCKED -> Text("Download blocked")
                else -> {}
            }
            Spacer(modifier = Modifier.height(8.dp))

            when (notificationInfo?.state) {
                WorkInfo.State.RUNNING -> Text("Applying notification...")
                WorkInfo.State.SUCCEEDED -> Text("Notification succeeded")
                WorkInfo.State.FAILED -> Text("Notification failed")
                WorkInfo.State.CANCELLED -> Text("Notification cancelled")
                WorkInfo.State.ENQUEUED -> Text("Notification enqueued")
                WorkInfo.State.BLOCKED -> Text("Notification blocked")
                else -> {}
            }
        }
    }
}